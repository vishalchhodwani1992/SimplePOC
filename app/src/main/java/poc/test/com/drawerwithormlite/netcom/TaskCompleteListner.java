package poc.test.com.drawerwithormlite.netcom;



public interface TaskCompleteListner {
    public abstract void onTaskComplete(int methodName, Object obj);
    public abstract void onTaskCompleteError(int methodName, Object obj);

}
