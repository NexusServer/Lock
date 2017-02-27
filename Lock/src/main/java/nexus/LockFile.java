package nexus;

public class LockFile{
private static LockFile instance = null;private LinkedHashMap<String,String> data;
pubtlic static LockFile getInstance(){
return instance;
}
public LockFile(Main plugin){
  data = new LinkedHashMap<String,String>();
  plugin.getConfig().getAll().forEach((key,value)->{
    data.put(key,value);
  });
  
}
  
  
  public String toString(Position pos){
    StringBuilder str = new StringBuilder();
    str.append(pos.getFloorX+":").append(pos.getFloorY()+":").append(pos.getFloorZ()+":").append(pos.getLevel().getFolderName());
    return str.toString();
  }
  public Position toPosition(String str){
    String[] s = str.spilt(":");
    Position pos = new Position(Integer.parseInt(s[0]),Integer.parseInt(s[1]),Integer.parseInt(s[2]),Server.getFolderByLevel(s[3]));
    return pos;
  }
  public boolean isOwner(Position pos,Player player){
  if(!data.containKey(pos)){
    return false;
  }
  String[] member = data.get(pos).sp???(":");
    return member[0].equals(player.getName().toLowerCase());
  
  }
  public boolean isLock(Position pos){
    return data.containKey(toString(pos));
  
  }
}
