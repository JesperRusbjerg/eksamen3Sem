/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * @author Jesper
 * @param <T>
 */
public class JPQLBuilderFacade<T> {

    List<Object> params = new ArrayList();
    boolean first = true;
    String JPQL = "";
    private final Class c;
    private Class dto;
    private String orderBy = "";
    private final String entity;

    //Hardest examples from semester
    // Query q1 = em.createQuery("SELECT p From Pet p WHERE p.id IN (SELECT e.pet.id FROM Event e where e.date = :date)");
    // TypedQuery<PersonDTO> tq = em.createQuery("select new dto.PersonDTO(p) From Person as p join p.phones ph where ph.number = :phonenumber", PersonDTO.class);
    // ph is a variable name that you use, p.phones is a list that person has
    // Select t from Teacher t where size(t.semesterCollection) = (select max(size(t.semesterCollection)) from Teacher t)"
    //Constructor if you want a DTO back.. First class is entity, second is the DTO you want returned
    public JPQLBuilderFacade(Class c, Class dto) {
        this.c = c;
        this.dto = dto;

        String[] dtoArr = dto.getName().split("\\ ");
        String findDto = dtoArr[dtoArr.length - 1];

        String[] arr = c.getName().split("\\.");
        entity = arr[arr.length - 1];

        JPQL += "Select new " + findDto + "(t) from " + entity + " t ";
//        addNumbers();
    }

    //Constructor if you want same object back, no DTO
    public JPQLBuilderFacade(Class c) {
        this.c = c;
        String[] arr = c.getName().split("\\.");
        entity = arr[arr.length - 1];
        JPQL += "Select t from " + entity + " t ";
//        addNumbers();
    }

    //Any param can be single added here
    public void addParam(String fieldName, Object value) {
        if (value == null || value.equals("0") || value.equals("")) {
        } else {
            params.add(value);
             int lastIndex = params.size();
            if (first) {
                System.out.println("lastindex: " + lastIndex);
                JPQL += "where t." + fieldName + " = ?" + lastIndex + " ";

                first = false;
            } else {
                System.out.println("lastindex: " + lastIndex);
                JPQL += "AND t." + fieldName + " = ?" + lastIndex + " ";
            }
        }
    }

    //Between = integers
    //Example = I want car size between 4 and 42, this fixes that in jpql
    public void addBetween(String fieldName, Object value1, Object value2) {

        boolean isValid = false;
        if (value1 == null || value2 == null || value1.equals("0") || value1.equals("") || value2.equals("0") || value2.equals("")) {
        } else {

            if (value1 instanceof Integer && value2 instanceof Integer) {
                int val1 = (Integer) value1;
                int val2 = (Integer) value2;
                System.out.println("First value of the between" + val1);
                System.out.println("Second value of the between " + val2);
                if (val1 > 0 && val2 > 0 && val1 < val2) {
                    isValid = true;
                }
            }

            if (isValid) {
                params.add(value1);
                int lastIndex = params.size();
                System.out.println("Last Index here:" + lastIndex);
                if (first) {
                    JPQL += "where t." + fieldName + " BETWEEN ?" + lastIndex + " ";
                    params.add(value2);
                    lastIndex = params.size();                    
//                    lastIndex = params.size() - 1;
                    JPQL += "AND ?" + lastIndex + " ";
                    first = false;
                } else {
                    JPQL += "AND t." + fieldName + " BETWEEN ?" + lastIndex + " ";
                    params.add(value2);
                     lastIndex = params.size(); 
//                    lastIndex = params.size() - 1;
                    JPQL += "AND ?" + lastIndex + " ";
                }
            }
        }
    }

    //Must give array here, Used spam the "OR" operator, not meant for multiple And's
    //Example: i want car.color either blue red green yellow, fieldname = color, if thats the field name in car, give array of colors aswell
    public void addMultipleParam(String fieldName, Object array[]) {
        if (array != null) {

            boolean wasFirst = false;

            if (first) {
                params.add(array[0]);

                int lastIndex = params.size();
                JPQL += "where (t." + fieldName + " = ?" + lastIndex + " ";

                first = false;
                wasFirst = true;
            }

            if (!wasFirst) {
                for (int i = 0; i < array.length; i++) {
                    params.add(array[i]);
                    int lastIndex = params.size();
                    if (i == 0) {
                        JPQL += "AND (t." + fieldName + " = ?" + lastIndex + " ";
                    } else {
                        JPQL += "OR t." + fieldName + " = ?" + lastIndex + " ";
                    }
                }
                JPQL += ")";
            } else {
                for (int i = 1; i < array.length; i++) {
                    params.add(array[i]);
                    int lastIndex = params.size();
                    if (i == 0) {
                        JPQL += "AND (t." + fieldName + " = ?" + lastIndex + " ";
                    } else {
                        JPQL += "OR t." + fieldName + " = ?" + lastIndex + " ";
                    }
                }
                JPQL += ")";
            }

        }
    }

    // Boolean ASC true = orders by DESC, false(the standard) = ASC
    //Orders by certain field name, isnt done till the end of the build.
    public void orderBy(String fieldName, boolean descendingOrAscending) {
        if (descendingOrAscending) {
            orderBy = "ORDER BY t." + fieldName + " DESC";
        } else {
            orderBy = "ORDER BY t." + fieldName + " ASC";
        }
    }

    //Builds list of objects
    public List<T> build() throws Exception {
        EntityManager em = Persistence.createEntityManagerFactory("pu").createEntityManager();
        JPQL += orderBy;
        System.out.println(JPQL);
        for (int i = 0; i < params.size(); i++) {
            System.out.println(i+1);
            System.out.println(params.get(i));
        }
        try{
        TypedQuery q = em.createQuery(JPQL, c);
        for (int i = 0; i < params.size(); i++) {
            q.setParameter(i+1, params.get(i));
        }
        return q.getResultList();
        }catch(Exception e){
           //Could throw exception to be caught by mapper here(This can also be caught, but maybe make a custom one)
        throw new Exception("No entity found from List build!");
        }
        
    }

    //Builds single object
    public T buildSingle() throws Exception {
        EntityManager em = Persistence.createEntityManagerFactory("pu").createEntityManager();
        JPQL += orderBy;
        System.out.println(JPQL);
        for (int i = 0; i < params.size(); i++) {
            System.out.println(i+1);
            System.out.println(params.get(i));
        }
        
        try{
        TypedQuery q = em.createQuery(JPQL, c);
        for (int i = 0; i < params.size(); i++) {
            q.setParameter(i+1, params.get(i));
        }
        try {
            return (T) q.getSingleResult();
        } catch (ClassCastException e) {
          //Could throw exception to be caught by mapper here(This can also be caught, but maybe make a custom one)
            throw new Exception("Could not convert to class!");
        }
        }catch(Exception e){
            //Could throw exception to be caught by mapper here(This can also be caught, but maybe make a custom one)
            throw new Exception("Did not find any entity!");
        }
    }

    //Exports string, mainly for testing
    public String exportJPQLString() {
        return JPQL;
    }

    
    //WORKS ONLY WITH BIDIRECTIONAL, THE SECOND CLASS MUST HAVE A REFERENCE TO THE FIRST(JOIN TABLE IS A GOOD ALTERNATIVE).
    //fieldName = name on first object, u will compare with in the second also, PK field is good here
    // doubleSelectClass = class of second select statement
    // fieldNameSecondSelect = normal param, where we select in the 2nd statement
    // valueSecondSelect = the value of which we search for
    // Example: "SELECT p From Pet p WHERE p.id(fieldName) IN (SELECT e.pet(doubleSelectClass).id FROM Event e where e.date(fieldNameSecondSelect) = :date(valueSecondselect))"
    public void doubleSelect(String fieldNamePrimaryKey, Class doubleSelectClass, String fieldNameSecondSelect, Object valueSecondSelect) {
        //Example, to show how its used
        // "SELECT p From Pet p WHERE p.id IN (SELECT e.pet.id FROM Event e where e.date = :date)"
        params.add(valueSecondSelect);

        //Getting name of second class
        String[] arr = doubleSelectClass.getName().split("\\.");
        String entitySecondClass = arr[arr.length - 1];

            int lastIndex = params.size();
        if (first) {
            System.out.println("lastindex: " + lastIndex);
            
            // IN = IN A COLLECTION. therefor this does not work with a one to one relation
            JPQL += "where t." + fieldNamePrimaryKey + " IN (SELECT x." + entity.toLowerCase() + "." + fieldNamePrimaryKey + " FROM " + entitySecondClass + " x where x." + fieldNameSecondSelect + " = ?" + lastIndex + ") ";
            first = false;
        } else {
            System.out.println("lastindex: " + lastIndex);
            JPQL += "AND t." + fieldNamePrimaryKey + " IN (SELECT x." + entity.toLowerCase() + "." + fieldNamePrimaryKey + " FROM " + entitySecondClass + " x where x." + fieldNameSecondSelect + " = ?" + lastIndex + ") ";
        }

    }

    //Join 2 tables and find object based on value in the table
    //For example: Find all people who has certain phone number, and have access to both Person and Phone values
    // listFieldName, would be person has "phone" so thats phone
    // joinClassFieldName would be the name within the "phone" so maybe "number"
    // joinClassFieldValue would then be the number thats in the database so the query can find it
    //WHEN JOINING 2 TABLES TOGETHER, YOU CANT GET ONE CLASS BACK, OR WELL YOU CAN, BUT THAT KILLS THE POINT
    // THEREFOR YOU WILL NEED A JOINED CLASS ENTITY"DATAHOLDER"
    //EXAMPLE: Person + phone could be "ContactInfoEntity" where you drag out both things, however it does this automatic
    // You wil get the fields youve made in the "ContactInfoEntityClass" :)
    public void joinTablesAndSelectCertainEntities(String listFieldName, String joinClassFieldName, Object joinClassFieldValue) {
        params.add(joinClassFieldValue);
            int lastIndex = params.size();
        if (first) {
            System.out.println("lastindex: " + lastIndex);
            JPQL += "join t." + listFieldName + " x where x." + joinClassFieldName + " = ?" + lastIndex + " ";
            first = false;
        } else {
            //Test this condition, not sure when its needed really, it will be very rare
            System.out.println("lastindex: " + lastIndex);
            JPQL += "AND join t." + listFieldName + " x where x." + joinClassFieldName + " = ?" + lastIndex + " ";
        }

    }

    //Finds the entity/entities, that has the biggest list
    //Select the teacher that has the biggest amount of students in their list
    //Find object/data that has the biggest of a List
    public void entitiesWithBiggestLists(String listFieldName) {
        if (first) {
            JPQL += "where size(t." + listFieldName + ") = (select max(size(t." + listFieldName + ")) from " + entity + " t) ";
            first = false;
        } else {
            JPQL += "AND size(t." + listFieldName + ") = (select max(size(t." + listFieldName + ")) from " + entity + " t) ";
        }
    }

   

}
