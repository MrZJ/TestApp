package test.com.commenlib.service;

/**
 * Created by zhangjian on 2019/5/15 10:53
 */
public class GlobalService {
    private static final GlobalService INSTANCE = new GlobalService();
    public static final String[] COMPONETS = {"test.com.minecomponent.MyApp", "test.com.logincomponent.MyApp"};

    private GlobalService() {

    }

    public static GlobalService getInstance() {
        return INSTANCE;
    }

    private IMineService iMineService;
    private ILoginService iLoginService;

    public void setiMineService(IMineService iMineService) {
        this.iMineService = iMineService;
    }

    public IMineService getiMineService() {
        if (iMineService == null) {
            return new EmptyIMineService();
        }
        return iMineService;
    }

    public void setiLoginService(ILoginService iLoginService) {
        this.iLoginService = iLoginService;
    }

    public ILoginService getiLoginService() {
        if (iLoginService == null) {
            return new EmptyLoginService();
        }
        return iLoginService;
    }

}
