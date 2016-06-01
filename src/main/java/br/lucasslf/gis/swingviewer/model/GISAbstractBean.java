package br.lucasslf.gis.swingviewer.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

/**
 * Classe NewClass
 */
public class GISAbstractBean implements Cloneable, Serializable {
    static final long serialVersionUID = 1L;
/**
     * PropertyChangeSupport usado para implementar o padrÃ£o JavaBeans e ser usado
     * pela API de binding
     */
    private transient PropertyChangeSupport support = new PropertyChangeSupport(this);
    
    /**
     * PadrÃ£o de clone do objeto deve criar nova instÃ¢ncia do PropertyChangeSupport
     * @return Clone raso do objeto
     */
    @Override
    public Object clone() {
        try {
            GISAbstractBean bean = (GISAbstractBean) super.clone();
            bean.support = new PropertyChangeSupport(bean);
            return bean;
        } catch (CloneNotSupportedException ex) {
            throw new IllegalStateException(ex);
        }
    }
    
    /**
     * Adiciona um listener das propriedades do bean. MÃ©todo padrÃ£o JavaBeans
     * @param listener O listener a ser adicionado.
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    /**
     * Remove um listener das propriedades do bean. MÃ©todo padrÃ£o JavaBeans
     * @param listener O listener a ser removido.
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }
    
    /**
     * Remove todos os listeners das propriedades do bean. MÃ©todo padrÃ£o JavaBeans
     */
    public void removeAllPropertyChangeListeners() {
        for (PropertyChangeListener propertyChangeListener : support.getPropertyChangeListeners()) {
            support.removePropertyChangeListener(propertyChangeListener);
        }
    }

    /**
     * ForÃ§a o evento de alteraÃ§Ã£o de propriedade
     * @param prop Nome da propriedade JavaBean
     * @param oldValue Valor antigo da propriedade
     * @param newValue Valor novo da propriedade
     */
    protected void firePropertyChange(String prop, Object oldValue, Object newValue) {
        support.firePropertyChange(prop, oldValue, newValue);
    }


    /**
     * Instacia o support apÃ³s deserializaÃ§Ã£o
     * ver: http://download.oracle.com/javase/6/docs/platform/serialization/spec/input.html#2971
     * @param stream Stream usado para leitura do objeto serializado
     * @throws IOException Erro na leitura
     * @throws ClassNotFoundException Classe nÃ£o encontrada
     */
    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        support = new PropertyChangeSupport(this);
    }
}
