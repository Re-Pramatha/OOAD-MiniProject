package club;

import club.enums.Table_Class;
import club.enums.Table_Type;

public class Game_Table extends Equipment {

    private Table_Type table_type;
    private Table_Class table_class;

    public Game_Table(long price, Table_Type table_type, Table_Class table_class) {
        super(price);
        this.code = "T-" + this.code;
        this.table_type = table_type;
        this.table_class = table_class;
    }

    public Table_Type getTable_type() { return this.table_type; }

    public Table_Class getTable_class() { return this.table_class; }

}
