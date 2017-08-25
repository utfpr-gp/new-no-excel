
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "useBean")
@SessionScoped
public class UseBean {

    String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String register() {
        return "thanks?faces-redirect=true";
    }

}
