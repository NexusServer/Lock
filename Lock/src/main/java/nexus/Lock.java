package nexus;


import java.util.ArrayList;
import java.util.LinkedHashMap;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
class ChestLock extends PluginBase implements Listener{
	/*
	 * Config
	 *  19:19:19:world : $sabone,console,nukkit ...
	 */
	public Config config;
	public LinkedHashMap<Player, String> share; //TODO : share 을 list에서 map 으로 변경
	public ArrayList<String> list;
	@Override
	public void onEnable(){
		this.getServer().getPluginManager().registerEvents(this, this);
		this.getDataFolder().mkdirs();
		this.config=new Config(this.getDataFolder()+"/config.yml",Config.YAML);
		this.getLogger().info("플러그인이 활성화 되었습니다.");
	}
	@Override
	public void onDisable(){
		this.getLogger().info("플러그인이 비활성화 되었습니다.");
		this.config.save();
	}
	
	@Override
	public boolean onCommand(CommandSender sender,Command cmd,String label,String[] args){
		if(!(sender instanceof Player)){
			sender.sendMessage("[Lock] 플레이어만 사용 가능한 명령어 입니다.");
		}
		else{
			Player player=(Player)sender;
			if(cmd.getName().equalsIgnoreCase("lock")){
				list.add(player.getName());
				player.sendMessage("[Lock] 원하는 상자/문을 터치해 잠그거나 잠금 해제하세요.");
			}
			if(cmd.getName().equalsIgnoreCase("share")){
				if(args.length<1){
					player.sendMessage("[Lock] /share [공유 대상]");
				}
				else{
					share.put(player, args[1]);
					player.sendMessage("[lock] 원하는 상자/문을 터치해 공유하거나 공유 해제하세요.");
				}
			}
		}
		return true;
	}
	@EventHandler
	public void onBreak(BlockBreakEvent event){
		Player player=event.getPlayer();
		String level=player.getLevel().getFolderName();
		Block block=event.getBlock();
		String xyz=block.getFloorX()+":"+block.getFloorY()+":"+block.getFloorZ()+":"+level;
		if(block.getId()==54||block.getId()==71||block.getId()==64||block.getId()==193||block.getId()==194||block.getId()==195||block.getId()==196||block.getId()==197){
			if(player.isOp()){
				return;
			}
			else if(this.config.get(xyz).toString().contains(player.getName())){
				player.sendMessage("[Lock] 잠겨있습니다.");
				event.setCancelled();
			}
		}
	}
	@EventHandler
	public void onTouch(PlayerInteractEvent event){
		Player player=event.getPlayer();
		String level=player.getLevel().getFolderName();
		Block block=event.getBlock();
		String xyz=block.getFloorX()+":"+block.getFloorY()+":"+block.getFloorZ()+":"+level;
		if(block.getId()==54||block.getId()==71||block.getId()==64||block.getId()==193||block.getId()==194||block.getId()==195||block.getId()==196||block.getId()==197){
			if(share.containsKey(player)){
				if(!this.config.get(xyz).toString().contains("$"+player.getName())){
					player.sendMessage("[Lock] 당신의 것이 아닙니다.");
					event.setCancelled();
				}
				else if(this.config.get(xyz).toString().contains(player.getName())){
					player.sendMessage("[Lock] 공유 해제");
					this.config.set(xyz,this.config.get(xyz).toString().replace(share.get(player), "null"));
					share.remove(player);
					event.setCancelled();
				}
				else{
					player.sendMessage("[Lock] 공유");
					this.config.set(xyz,this.config.get(xyz).toString()+","+share.get(player));
					share.remove(player);
					event.setCancelled();
				}
			}
			if(list.contains(player)){
				if(this.config.get(xyz).toString().startsWith("$"+player.getName())){
					player.sendMessage("[Lock] 잠금 해데");
					this.config.set(xyz, "false");
					list.remove(player);
					event.setCancelled();
				}
				else if(this.config.get(xyz).toString().equals("false")){
					player.sendMessage("[Lock] 잠금");
					this.config.set(xyz, "$"+player.getName());
					list.remove(player);
					event.setCancelled();
				}
				else{
					player.sendMessage("[Lock] 당신의 것이 아닙니다.");
					event.setCancelled();
				}
			}
			else{
				if(player.isOp()){
					return;
				}
				if(this.config.get(xyz).toString().contains(player.getName())){
					return;
				}
				else{
					player.sendMessage("[Lock] 권한이 없습니다.");
					event.setCancelled();
				}
			}
		}
	}
}