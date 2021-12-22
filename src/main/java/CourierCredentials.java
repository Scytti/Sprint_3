public class CourierCredentials {
    public final String login;
    public final String password;

    public CourierCredentials(String login, String password){
        this.login = login;
        this.password = password;
    }

    public static  CourierCredentials getCourierCredentials(Courier courier){
        return new CourierCredentials(courier.login, courier.password);
    }

    public static  CourierCredentials getCourierCredentialsWithoutLogin(Courier courier){
        return new CourierCredentials("", courier.password);
    }

    public static  CourierCredentials getCourierCredentialsWithoutPassword(Courier courier){
        return new CourierCredentials(courier.login, "");
    }

    public static  CourierCredentials getChangedLoginCourierCredentials(Courier courier){
        return new CourierCredentials(courier.login + "qri", courier.password);
    }

    public static  CourierCredentials getChangedPasswordCourierCredentials(Courier courier){
        return new CourierCredentials(courier.login, courier.password + "645");
    }
}
