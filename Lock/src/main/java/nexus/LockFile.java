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
  String[] member = data.get(pos).spilt(":");
    return member[0].equals(player.getName().toLowerCase());
  
  }
  public List<String> getMembers(Position pos){
    if(!isLock(pos)){
    return null;
    }
     List<String> list = new ArrayList<String>():
    String[] mem = data.get(toString(pos)).spilt(":");
    for(String str : mem){
    list.add(str);
    }
    return list;
  }
  public void addMember(Position pos,Player player){
  if(!isLock(pos)){
  return;
  }
    List<String> list = getMembers(pos);
    list.add(player.getName().toLowerCase());
    data.put(toString(pos),String.join(":",list.toArray()));
    return;
  }
  /*
  * return is LockBlock
  */
  public boolean isLock(Position pos){
    return data.containKey(toString(pos));
  }
  /*
  * shareList del {player}
  */
  public void delMember(Position pos, Player player){
    if(!isLock(pos)){
  return;
  }
    List<String> list = getMembers(pos);
    list.remove(player.getName().toLowerCase());
    data.put(toString(pos),String.join(":",list.toArray()));
    return;
  }
}
