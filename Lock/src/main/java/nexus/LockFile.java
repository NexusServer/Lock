package nexus;

public class LockFile{
private static LockFile instance = null;private LinkedHashMap<Position,String> data;
pubtlic static LockFile getInstance(){
return instance;
}
public LockFile(Main plugin){

}
}
