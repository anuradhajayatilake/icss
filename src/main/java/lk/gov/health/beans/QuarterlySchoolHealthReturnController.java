package lk.gov.health.beans;

import lk.gov.health.schoolhealth.QuarterlySchoolHealthReturn;
import lk.gov.health.beans.util.JsfUtil;
import lk.gov.health.beans.util.JsfUtil.PersistAction;
import lk.gov.health.faces.QuarterlySchoolHealthReturnFacade;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;

@Named
@SessionScoped
public class QuarterlySchoolHealthReturnController implements Serializable {

    @EJB
    private lk.gov.health.faces.QuarterlySchoolHealthReturnFacade ejbFacade;
    private List<QuarterlySchoolHealthReturn> items = null;
    private QuarterlySchoolHealthReturn selected;

    public QuarterlySchoolHealthReturnController() {
    }

    public QuarterlySchoolHealthReturn getSelected() {
        return selected;
    }

    public void setSelected(QuarterlySchoolHealthReturn selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private QuarterlySchoolHealthReturnFacade getFacade() {
        return ejbFacade;
    }

    public QuarterlySchoolHealthReturn prepareCreate() {
        selected = new QuarterlySchoolHealthReturn();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("QuarterlySchoolHealthReturnCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("QuarterlySchoolHealthReturnUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("QuarterlySchoolHealthReturnDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<QuarterlySchoolHealthReturn> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public List<QuarterlySchoolHealthReturn> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<QuarterlySchoolHealthReturn> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = QuarterlySchoolHealthReturn.class)
    public static class QuarterlySchoolHealthReturnControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            QuarterlySchoolHealthReturnController controller = (QuarterlySchoolHealthReturnController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "quarterlySchoolHealthReturnController");
            return controller.getFacade().find(getKey(value));
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof QuarterlySchoolHealthReturn) {
                QuarterlySchoolHealthReturn o = (QuarterlySchoolHealthReturn) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), QuarterlySchoolHealthReturn.class.getName()});
                return null;
            }
        }

    }

}
