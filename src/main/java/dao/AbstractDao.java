package dao;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import Connection.ConnectionFactory;

import static Connection.ConnectionFactory.getConnection;

public class AbstractDao<T> {

    protected static final Logger LOGGER = Logger.getLogger(AbstractDao.class.getName());

    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public AbstractDao() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    private String createSelectQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + field + " =?");
        return sb.toString();
    }

    /**
     *  <p>
     *    This is a mysql query for a select all action.
     *    I've worked with a string builder to create the query, so the table from where we are selecting all the rows
     *    is type.getSimpleName() where type is a generic class.
     * </p>
     *
     * @return sb.toString()
     */

    private String createSelectQueryFindAll() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        return sb.toString();
    }

    /**
     * <p>
     *    This is a mysql query for a delete action.
     *    I've worked with a string builder to create the query, so the table from where we are deleting is
     *    type.getSimpleName() where type is a generic class.
     * </p>
     * @param id is the id that we are searching to delete.
     * @return sb.tostring()
     */
    private String createDeleteQuery(int id) {
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE ");
        sb.append("FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE ");
        sb.append("id= ");
        sb.append(String.valueOf(id));
        return sb.toString();
    }

    private String createInsertQuery() {
        StringBuilder sb = new StringBuilder();
        Field[] fields = type.getDeclaredFields();
        sb.append("INSERT ");
        sb.append(" INTO ");
        sb.append(type.getSimpleName().toLowerCase());
        sb.append(" (");
        for (int i = 1; i < fields.length; i++) {
            if (i == fields.length - 1) {
                sb.append(fields[i].getName()).append(") ");
            } else {
                sb.append(fields[i].getName()).append(",");
            }
        }
        sb.append(" VALUES");
        sb.append("(");
        for (int i = 1; i < fields.length; i++) {
            if (i == fields.length - 1) {
                sb.append("?");
            } else {
                sb.append("?,");
            }
        }
        sb.append(")");
        return sb.toString();
    }

    private String createaUpdateQuery(int id) {
        StringBuilder sb = new StringBuilder();
        Field[] fields = type.getDeclaredFields();
        sb.append("Update ");
        sb.append(type.getSimpleName()).append(" ");
        sb.append("SET ");
        for (int i = 1; i < fields.length; i++) {
            if (i == fields.length - 1) {
                sb.append(fields[i].getName()).append("=").append("?");
            } else {
                sb.append(fields[i].getName()).append("=").append("?,");
            }
        }
        sb.append(" WHERE ");
        sb.append("id = ");
        sb.append(String.valueOf(id));
        return sb.toString();
    }


    public T findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("id");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            return createObjects(resultSet).get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();
        Constructor[] ctors = type.getDeclaredConstructors();
        Constructor ctor = null;
        for (int i = 0; i < ctors.length; i++) {
            ctor = ctors[i];
            if (ctor.getGenericParameterTypes().length == 0)
                break;
        }
        try {
            while (resultSet.next()) {
                ctor.setAccessible(true);
                T instance = (T) ctor.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<T> findAll() {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement statement = null;
        String query = createSelectQueryFindAll();
        try {
            connection = getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            return createObjects(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    public boolean insert(T t, String[] dates) {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = createInsertQuery();
        try {
            connection = getConnection();
            statement = connection.prepareStatement(query);
            create(statement,dates);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return false;
    }

    public void create(PreparedStatement statement, String[] dates) throws SQLException {
        Field[] fields = type.getDeclaredFields();
        for (int i = 1; i < fields.length; i++) {
            String aux;
            aux = fields[i].getType().getSimpleName();
            switch (aux) {
                case "String":
                    statement.setString(i, dates[i]);
                    break;
                case "int":
                    statement.setInt(i, Integer.parseInt(dates[i]));
                    break;
            }
        }
    }

    public boolean delete(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = createDeleteQuery(id);
        try {
            connection = getConnection();
            statement = connection.prepareStatement(query);
            statement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return false;
    }

    public void uptdateVariable(PreparedStatement statement, String[] dates) throws SQLException {
        Field[] fields = type.getDeclaredFields();
        for (int i = 1; i < fields.length; i++) {
            String aux;
            aux = fields[i].getType().getSimpleName();
            switch (aux) {
                case "String":
                    statement.setString(i, dates[i]);
                    break;
                case "int":
                    statement.setInt(i, Integer.parseInt(dates[i]));
                    break;
            }
        }
    }

    public boolean update(int id, String[] dates) {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = createaUpdateQuery(id);
        try {
            connection = getConnection();
            statement = connection.prepareStatement(query);
            uptdateVariable(statement, dates);
            statement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return false;
    }

    public List<String> getHeader() {
        List<String> header = new ArrayList<>();
        Field[] fields = type.getDeclaredFields();
        for (Field i : fields) {
            header.add(i.getName());
        }
        return header;
    }
    public String[] extract(T t){
        String[] data = new String[type.getDeclaredFields().length];
        Field[] fields = t.getClass().getDeclaredFields();
        try{
            for (int i = 0 ; i < fields.length; i++){
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fields[i].getName(),t.getClass());
                data[i] = String.valueOf(propertyDescriptor.getReadMethod().invoke(t));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return data;
    }

    public String[] ListToString(List<T> t)  {
        String[] data = new String[findAll().size()];
        for (int i = 0; i < findAll().size(); i++) {
            data[i] = t.get(i).toString();
        }
        return data;
    }

    public String[][] listToMatrix(List<T> list){
        if (list != null) {
            String[][] matrix = new String[list.size()][];
            for (int i = 0; i < list.size(); i++)
                matrix[i] = extract(list.get(i));
            return matrix;
        }else {
            String[][] matrix = new String[1][];
            matrix[0] = new String[type.getDeclaredFields().length];
            for (int i = 0 ; i < type.getDeclaredFields().length; i++)
                matrix[0][i] = "";
            return matrix;
        }
    }

    public boolean createBill(String[] dates) {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = createInsertQuery();
        try {
            connection = getConnection();
            statement = connection.prepareStatement(query);
            create(statement,dates);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return false;
    }

    public Class<T> Type(){
        return type;
    }
}
