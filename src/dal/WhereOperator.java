package dal;

/**
 * Created by IntelliJ IDEA.
 * User: Patrick Stapfer
 * Date: 13.04.12
 * Time: 15:20
 */
public enum WhereOperator {
    EQUALS{
        public String toString() {
            return "=";
        }
    },
    NOT{
        public String toString() {
            return "NOT";
        }
    },
    GREATER{
        public String toString() {
            return ">";
        }
    },
    GREATEREQUALS{
        public String toString(){
            return ">=";
        }
    },
    LOWER{
        public String toString() {
            return "<";
        }
    },
    LOWEREQUALS{
        public String toString(){
            return "<=";
        }
    }


}
