package dal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Patrick Stapfer
 * Date: 13.04.12
 * Time: 16:52
 */
public class WhereChain {
    private List<WhereCondition> conditions = new ArrayList<WhereCondition>();

    public WhereChain(String fieldname, WhereOperator operator, Object value){
        init(fieldname, operator, value);
    }

    /**
     * This method REINITIALIZES the WhereChain and sets the initial condition, the base of the whole chain on
     * which each other condition builds on
     * Warning: Existing where-conditions will be dropped!
     * @param fieldname - Fieldname for the where condition
     * @param operator - WhereOperator for comparison
     * @param value - Value for the field-condition
     */
    public void init(String fieldname, WhereOperator operator, Object value){
        if(!conditions.isEmpty())
            clear();

        addCondition(null, fieldname, operator, value);
    }

    public void addAndCondition(String fieldname, WhereOperator operator, Object value){
        addCondition(Chainer.AND, fieldname, operator, value);
    }

    public void addOrCondition(String fieldname, WhereOperator operator, Object value){
        addCondition(Chainer.OR, fieldname, operator, value);
    }

    private void addCondition(Chainer chainer, String fieldname, WhereOperator operator, Object value){
        conditions.add(new WhereCondition(chainer, fieldname, operator, value));
    }

    protected List<WhereCondition> getConditions(){
        return conditions;
    }

    public void clear(){
        conditions.clear();
    }

    public static enum Chainer {
        AND{
            public String toString(){
                return "AND";
            }
        },
        OR{
            public String toString(){
                return "OR";
            }
        },
    }

}
