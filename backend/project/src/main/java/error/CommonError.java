package error;

/**
 * @author czf
 * @Date 2020/4/9 11:27 下午
 */
public interface CommonError {
    public int getErrCode();
    public String getErrMsg();
    public CommonError setErrMsg(String errMsg);
}
