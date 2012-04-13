package dal;

/**
 * Created by IntelliJ IDEA.
 * User: Patrick Stapfer
 * Date: 13.04.12
 * Time: 15:19
 */
public class WhereCondition {
    private String fieldname = null; //Field, which is checked for a specific value
    private WhereOperator operator = null; //Which constraint between field and fieldvalue
    private Object value = null; //Field value for the compare-procedure
    private WhereChain.Chainer chainer = null; //Defines, how it will be chained to other WhereConditions

    /**
     * This constructor creates a chained WhereCondition, that means when the Condition is used, it should be
     * treated with a chain-word first (AND, OR)
     * @param chainer - Chain-Word for lead
     * @param fieldname - Fieldname for the where condition
     * @param operator - WhereOperator for comparison
     * @param value - Value for the field-condition
     */
    protected WhereCondition(WhereChain.Chainer chainer, String fieldname, WhereOperator operator, Object value){
        this.chainer = chainer;
        this.fieldname = fieldname;
        this.operator = operator;
        this.value= value;
    }

    /**
     * Constructs an independent WHERE-Condition.
     * @param fieldname - Fieldname for the where condition
     * @param operator - WhereOperator for comparison
     * @param value - Value for the field-condition
     */
    public WhereCondition(String fieldname, WhereOperator operator, Object value) {
        this(null, fieldname, operator, value);
    }

    public String getFieldname() {
        return fieldname;
    }

    public WhereOperator getOperator() {
        return operator;
    }

    public Object getValue() {
        return value;
    }

    public WhereChain.Chainer getChainer() {
        return chainer;
    }

    public void setFieldname(String fieldname) {
        this.fieldname = fieldname;
    }

    public void setOperator(WhereOperator operator) {
        this.operator = operator;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public void setChainer(WhereChain.Chainer chainer) {
        this.chainer = chainer;
    }
}
