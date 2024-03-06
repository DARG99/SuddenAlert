/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frontend;

import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 *
 * @author Catarina
 */
public class TeclasPermitLocalizacao extends PlainDocument {

    @Override
    public void insertString(int offset, String str, javax.swing.text.AttributeSet attr)
            throws BadLocationException {
        super.insertString(offset, str.replaceAll("[^0-9|^,|^.|^º|^'|^-]", ""), attr);
    }
    
    public void replace(int offset, String str, javax.swing.text.AttributeSet attr)
          throws BadLocationException {
        super.insertString(offset, str.replaceAll("[^0-9|^,|^.|^º|^'|^-]", ""), attr);
    }  
}