package com.qgs.ui.util.table;

import com.vaadin.data.Property;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;

/**
 *
 * @author rafael
 */
public class CheckBoxColumnGenerator implements ColumnGenerator {

    private String originId = "";
    private boolean output = true;
    private String capt = null;

    public CheckBoxColumnGenerator(String originPropId, boolean readOnly, String caption) {
        originId = originPropId;
        output = readOnly;
        capt = caption;
    }

    @Override
    public Object generateCell(Table source, Object itemId, Object columnId) {
        Property prop = source.getItem(itemId).getItemProperty(originId);
        boolean val = (Boolean) prop.getValue();

        CheckBox ret = new CheckBox(capt, val);
        ret.setReadOnly(output);

        return ret;
    }
}
