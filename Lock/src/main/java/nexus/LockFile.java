package nexus;

public class LockFile{
private static LockFile instance = null;private LinkedHashMap<Position,String> data;
pubtlic static LockFile getInstance(){
return instance;
}
public LockFile(Main plugin){
  plugin.getConfig();
}
  
  
  public String toString(Position pos){
    StringBuilder str = new StringBuilder();
    str.append(pos.getFloorX+":").append(pos.getFloorY()+":").append(pos.getFloorZ()+":").append(pos.getLevel().getFolderName());
    return str.toString();
  }
}