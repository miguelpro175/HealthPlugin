import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;

public class s3HealthListener extends PluginListener
{
	public class p1 {
		public String name;
		public int hp;
		public int exp = 0;
		public int melee = 1;
		public int oxygen = 15;
		public int onfire = 0;
		// Combat Log on by default
		//public int combatlog = 1;


		public p1(String name, int hp)
		{
			this.name = name;
			this.hp = hp;

		}
	}

	boolean pvp = s3HealthPlugin.pvp;
	boolean dropinventory = s3HealthPlugin.dropinventory;
	int Combattimer = s3HealthPlugin.Combattimer;
    
	int woodensword = s3HealthPlugin.woodensword;
	int stonesword = s3HealthPlugin.stonesword;
	int ironsword = s3HealthPlugin.ironsword;
	int goldsword = s3HealthPlugin.goldsword;
	int diamondsword =s3HealthPlugin.diamondsword;

	int woodenspade = s3HealthPlugin.woodenspade;
	int stonespade = s3HealthPlugin.stonespade;
	int ironspade = s3HealthPlugin.ironspade;
	int goldspade = s3HealthPlugin.goldspade;
	int diamondspade = s3HealthPlugin.diamondspade;
    
	int woodenpickaxe = s3HealthPlugin.woodenpickaxe;
	int stonepickaxe = s3HealthPlugin.stonepickaxe;
	int ironpickaxe = s3HealthPlugin.ironpickaxe;
	int goldpickaxe = s3HealthPlugin.goldpickaxe;
	int diamondpickaxe = s3HealthPlugin.diamondpickaxe;
    
	int woodenaxe = s3HealthPlugin.woodenaxe;
	int stoneaxe = s3HealthPlugin.stoneaxe;
	int ironaxe = s3HealthPlugin.ironaxe;
	int goldaxe = s3HealthPlugin.goldaxe;
	int diamondaxe = s3HealthPlugin.diamondaxe;

	int basedamage = s3HealthPlugin.basedamage;
	int maxhealth = s3HealthPlugin.maxhealth;
	 
	public Timer timer;
	public Timer saveTimer;
	
	public ArrayList<p1> playerList;
	public s3HealthListener()
	{
		this.timer = new Timer();
		// get from Combattimer property
		//this.timer.schedule(new RemindTask(this), Combattimer);
		
		timer.scheduleAtFixedRate(new RemindTask(this), 0, Combattimer);
		
		System.out.println(getDateTime() + " [INFO] Melee Combat Task Scheduled.");
		playerList = new ArrayList<p1>();
		
		loadPlayerList();
		
		// Set save
		//this.saveTimer = new Timer();
		//this.timer.schedule(new SaveCombat(this), 100000L);
		
		
		//Timer timer = new Timer();
		//timer.scheduleAtFixedRate(new SaveCombat(this), 5000, 20000);
		
		
		//System.out.println(getDateTime() + " [INFO] Combat saving scheduled.");
		System.out.println(getDateTime() + " [DEBUG] s3HealthPlugin - Listener PVP:" + pvp);

		
	}
	
	public boolean onBlockCreate(Player player, Block BlockPlaced, Block blockClicked, int itemInHand)
	{
		if (itemInHand == 319)
		{
			setPlayerHP(player, getPlayerHP(player) + 2);
			player.sendMessage("HP:" + getPlayerHP(player));
		} else {
			if (itemInHand == 320)
			{
				setPlayerHP(player, getPlayerHP(player) + 4);
				player.sendMessage("HP:" + getPlayerHP(player));
			} else {
				if (itemInHand == 297)
				{
					setPlayerHP(player, getPlayerHP(player) + 3);
					player.sendMessage("HP:" + getPlayerHP(player));
				} else {
					if (itemInHand == 260)
					{
						setPlayerHP(player, getPlayerHP(player) + 2);
						player.sendMessage("HP:" + getPlayerHP(player));
					} else {
						if (itemInHand == 282)
						{
							setPlayerHP(player, getPlayerHP(player) + 5);
							player.sendMessage("HP:" + getPlayerHP(player));
						} else {
							if (itemInHand == 322)
							{
								setPlayerHP(player, getPlayerHP(player) + 10);
								player.sendMessage("HP:" + getPlayerHP(player));
							}
						}
					}
				}
			}
		}
		return false;
	}  
	
	public void loadPlayerList()
	{
		// populate the playerlist with previous data
		
		try {
			String line;
			DataInputStream in = new DataInputStream(new FileInputStream("s3HealthPlugin.txt"));
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			
			while ((line = br.readLine()) != null) {
				// loop through each player
				
				// # ? skip it...
				if (line.substring(0,1).matches("[#]"))
				{
					System.out.println(getDateTime() + " [DEBUG] Comment Skipped");
				} else {
				
					// first remove the \'s
					String slashedstring;
					slashedstring = line.replace("\\:",":");
					
					String[] tokens = slashedstring.split("=");
					
					String[] params = tokens[1].split(":");
					
					// hp
					int curhp = Integer.parseInt(params[0]);
					// exp
					int curexp = Integer.parseInt(params[1]);
					// melee					
					int curmelee = Integer.parseInt(params[2]);
					// oxygen
					int curoxygen = Integer.parseInt(params[3]);
					int curonfire = Integer.parseInt(params[4]);
					p1 curplayer = new p1(tokens[0], curhp);
					curplayer.exp = curexp;
					curplayer.melee = curmelee;
					curplayer.oxygen = curoxygen;
					curplayer.onfire = curonfire;
					this.playerList.add(curplayer);
					System.out.println(getDateTime() + " [DEBUG] new player: " + curplayer.name + " added with: " + curplayer.hp + ":" + curplayer.exp + ":" + curplayer.melee + ":" + curplayer.oxygen + ":" + curplayer.onfire);
					
					
				}
			}
			in.close();
			br.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	public void packParameters()
	{
		PropertiesFile configProperties = new PropertiesFile("s3HealthPlugin.properties");
		configProperties.setBoolean("pvp", pvp);
		configProperties.setBoolean("drop-inventory", dropinventory);
		configProperties.setInt("combat-timer", Combattimer);
		configProperties.setInt("wooden-sword", woodensword);
		configProperties.setInt("stone-sword", stonesword);
		configProperties.setInt("iron-sword", ironsword);
		configProperties.setInt("gold-sword", goldsword);
		configProperties.setInt("diamond-sword", diamondsword);
		configProperties.setInt("wooden-spade", woodenspade);
		configProperties.setInt("stone-spade", stonespade);
		configProperties.setInt("iron-spade", ironspade);
		configProperties.setInt("gold-spade", goldspade);
		configProperties.setInt("diamond-spade", diamondspade);
		configProperties.setInt("wooden-pickaxe", woodenpickaxe);
		configProperties.setInt("stone-pickaxe", stonepickaxe);
		configProperties.setInt("iron-pickaxe", ironpickaxe);
		configProperties.setInt("gold-pickaxe", goldpickaxe);
		configProperties.setInt("diamond-pickaxe", diamondpickaxe);
		configProperties.setInt("wooden-axe", woodenaxe);
		configProperties.setInt("stone-axe", stoneaxe);
		configProperties.setInt("iron-axe", ironaxe);
		configProperties.setInt("gold-axe", goldaxe);
		configProperties.setInt("diamond-axe", diamondaxe);
		configProperties.setInt("basedamage", basedamage);
		configProperties.setInt("maxhealth", maxhealth);	
	}
	
	public void packPlayers()
	{
		// Packs all players stored in ArrayList playerList
		// into the configPlayers file
		PropertiesFile configPlayers = new PropertiesFile("s3HealthPlugin.txt");
		for (int i = 0; i < this.playerList.size(); i++) {
			String playerData = this.playerList.get(i).hp + ":" + this.playerList.get(i).exp + ":" + this.playerList.get(i).melee + ":" + this.playerList.get(i).oxygen + ":" + this.playerList.get(i).onfire;
			configPlayers.setString(this.playerList.get(i).name, playerData);
		}
		//configPlayers.close();
		// too spammy
		System.out.println(getDateTime() + " [DEBUG] s3HealthPlugin player data saved.");
	}
	
	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);
	}

	private double getDistance(Player a, Mob b)
	{
		double xPart = Math.pow(a.getX() - b.getX(), 2.0D);
		double yPart = Math.pow(a.getY() - b.getY(), 2.0D);
		double zPart = Math.pow(a.getZ() - b.getZ(), 2.0D);
		return Math.sqrt(xPart + yPart + zPart);
	}

	private double getPlayerDistance(Player a, Player b)
	{
		double xPart = Math.pow(a.getX() - b.getX(), 2.0D);
		double yPart = Math.pow(a.getY() - b.getY(), 2.0D);
		double zPart = Math.pow(a.getZ() - b.getZ(), 2.0D);
		return Math.sqrt(xPart + yPart + zPart);
	}

	public int getPlayerHP(Player player)
	{
		for (int i = 0; i < this.playerList.size(); i++) {
			if (this.playerList.get(i).name.equals(player.getName()) == true)
			{
				return this.playerList.get(i).hp;
			}
		}
		return 0;
	}

	public int getPlayerOnFire(Player player)
	{
		for (int i = 0; i < this.playerList.size(); i++) {
			if (this.playerList.get(i).name.equals(player.getName()) == true)
			{
				return this.playerList.get(i).onfire;
			}
		}
		return 0;
	}

	public void setPlayerOnFire(Player player, Integer newfire)
	{
		for (int i = 0;i < this.playerList.size(); i++) {
			if (this.playerList.get(i).name.equals(player.getName()) == true)
			{
				this.playerList.get(i).onfire = newfire;
			}
		}
	}

	public void setPlayerHP(Player player, Integer newhp)
	{
		for (int i = 0; i < this.playerList.size(); i++) {
			if (this.playerList.get(i).name.equals(player.getName()) == true)
			{
				if (newhp <= (maxhealth + this.playerList.get(i).melee))
				{
					this.playerList.get(i).hp = newhp;
				} else {
					this.playerList.get(i).hp = (maxhealth + this.playerList.get(i).melee);
				}

			}
		}
	}

	public int getPlayerMelee(Player player)
	{
		for (int i = 0; i < this.playerList.size(); i++) {
			if (this.playerList.get(i).name.equals(player.getName()) == true)
			{
				return this.playerList.get(i).melee;
			}
		}
		return 0;
	}
	
	public int getPlayerOxygen(Player player)
	{
		for (int i = 0; i < this.playerList.size(); i++) {
			if (this.playerList.get(i).name.equals(player.getName()) == true)
			{
				return this.playerList.get(i).oxygen;
			}
		}
		return 0;
	}

	public void setPlayerOxygen(Player player, Integer newoxygen)
	{
		for (int i = 0; i < this.playerList.size(); i++) {
			if (this.playerList.get(i).name.equals(player.getName()) == true)
			{
				this.playerList.get(i).oxygen = newoxygen;
			}
		}
	}

	public boolean onCommand(Player player, String[] split)
	{
		if(split[0].equalsIgnoreCase("/health") && player.canUseCommand("/health"))
		{
			player.sendMessage("HP:" + getPlayerHP(player));
			return true;
		}
		
		if(split[0].equalsIgnoreCase("/hp") && player.canUseCommand("/health"))
		{
			player.sendMessage("HP:" + getPlayerHP(player));
			return true;
		}


		
		if(split[0].equalsIgnoreCase("/pvpenable") && player.canUseCommand("/pvpenable"))
        {
          pvp = true; // false = Disabled, true = Enabled
          player.sendMessage("PVP Enabled");
          return true;
		}

		if(split[0].equalsIgnoreCase("/pvpdisable") && player.canUseCommand("/pvpdisable"))
        {
          pvp = false; //false = Disabled, true = Enabled
          player.sendMessage("PVP Disabled");
          return true;
		}

		if(split[0].equalsIgnoreCase("/heal") && player.canUseCommand("/heal"))
		{
			setPlayerHP(player,100);
			player.sendMessage("You have been fully healed. HP:" + getPlayerHP(player));
			return true;
		}
		
		if(split[0].equalsIgnoreCase("/rotation") && player.canUseCommand("/rotation"))
		{
			player.sendMessage(String.valueOf(player.getRotation() % 360));
			return true;
		}
		return false;
	}
	
	public void onDisconnect(Player player)
	{
		packPlayers();
	}

	public void onLogin(Player player)
	{
		// check if the player exists
		int exists = 0;

		for (int i = 0; i < this.playerList.size(); i++) {
			if (this.playerList.get(i).name.equals(player.getName()) == true)
			{
				exists = 1;
				player.sendMessage("Welcome back! HP:" + getPlayerHP(player));
			} else {
			}
		}

		if (exists == 0)
		{
			p1 play = new p1(player.getName(),10);

			this.playerList.add(play);
			player.sendMessage("Welcome, you have been registered by the hp system! HP:" + getPlayerHP(player));
		}
	}

	public void GiveExperience(Player player, int amount)
	{
		//player.sendMessage("Pending experience...");
		int playerfound = 0;
		for (int i = 0; i < this.playerList.size(); i++) {
			if (this.playerList.get(i).name.equals(player.getName()) == true)
			{
				playerfound = 1;
				this.playerList.get(i).exp = this.playerList.get(i).exp + amount;
				player.sendMessage("You gain experience (" + this.playerList.get(i).exp + ")!");
				//Random generator = new Random();
				//int index = generator.nextInt(100);
				// 1 in a hundred chance of skillup
				if (this.playerList.get(i).exp >= (this.playerList.get(i).melee * 2000))
				{
					this.playerList.get(i).melee = this.playerList.get(i).melee + 1;
					player.sendMessage("You have gained a level! (" + this.playerList.get(i).melee + ")!");
					setPlayerHP(player,100);
					player.sendMessage("HP: " + getPlayerHP(player));
				}

			}
		}
	}

	public int PlayerHasHit(Player player)
	{
		int melee = getPlayerMelee(player);
		Random generator = new Random();
		int index = generator.nextInt(10);
		if (index + melee > 5)
		{
			return 1;    	  
		}
		return 0;
	}

	public void DropPlayerItems(Player player)
	{
		// drop items
		// Loop through the inventory slots removing each item.
		for(int slot=0;slot<36;slot++)
		{
			// what's the item id?
			try {
				Item item = player.getInventory().getItemFromSlot(slot);
				int itemid = item.getItemId();
				int amount = item.getAmount();


				// dupe the item to the location of the player
				player.giveItemDrop(itemid, amount);
			} 
			catch (NullPointerException e)
			{
				// no item

			}

			// Remove the item from the slot.
			player.getInventory().removeItem(slot);


		}
		// Make sure we send a inventory update to the player so there client gets the changes.
		player.getInventory().updateInventory();
	}
	public void DoPlayerDeath(Player player)
	{
	      // slain
	      //player.sendMessage("You have been slain");
	      
	      if (dropinventory == true )
	      {
	          DropPlayerItems(player);
	      }
	      
	      // warp to spawn
	      player.teleportTo(etc.getServer().getSpawnLocation());
	      setPlayerHP(player,100);
	}

	public String getItemName(int itemId)
	{
		// <for future use>

		// incase there is no item or something we don't have in the list
		String itemname = "fashioned weapon";

		if (itemId == 268)
		{
			// Wooden Sword
			itemname = "Wooden Sword";
		}

		if (itemId == 272)
		{
			// Stone Sword
			itemname = "Stone Sword";
		}

		if (itemId == 267)
		{
			// Iron Sword
			itemname = "Iron Sword";
		}

		if (itemId == 283)
		{
			// Gold Sword
			itemname = "Gold Sword";
		}

		if (itemId == 276)
		{
			// Diamond Sword
			itemname = "Diamond Sword";
		}

		return itemname;
	}

	public int getItemDamage(int itemId)
	{
		// in case there is no item found, use the base damage for a 'fashioned weapon' (ie brick etc) (3)
		int itembasedamage = basedamage;
		if (itemId == 268)
		{
			itembasedamage = woodensword;
		}
		if (itemId == 269)
		{
			itembasedamage = woodenspade;
		}
		if (itemId == 270)
		{
			itembasedamage = woodenpickaxe;
		}
		if (itemId == 271)
		{
			itembasedamage = woodenaxe;
		}
		if (itemId == 272)
		{
			itembasedamage = stonesword;
		}
		if (itemId == 273)
		{
			itembasedamage = stonespade;
		}
		if (itemId == 274)
		{
			itembasedamage = stonepickaxe;
		}
		if (itemId == 275)
		{
			itembasedamage = stoneaxe;
		}
		if (itemId == 276)
		{
			itembasedamage = diamondsword;
		}
		if (itemId == 277)
		{
			itembasedamage = diamondspade;
		}
		if (itemId == 278)
		{
			itembasedamage = diamondpickaxe;
		}
		if (itemId == 279)
		{
			itembasedamage = diamondaxe;
		}
		if (itemId == 267)
		{
			itembasedamage = ironsword;
		}
		if (itemId == 256)
		{
			itembasedamage = ironspade;
		}
		if (itemId == 257)
		{
			itembasedamage = ironpickaxe;
		}
		if (itemId == 258)
		{
			itembasedamage = ironaxe;
		}
		if (itemId == 283)
		{
			itembasedamage = goldsword;
		}
		if (itemId == 284)
		{
			itembasedamage = goldspade;
		}
		if (itemId == 285)
		{
			itembasedamage = goldpickaxe;
		}
		if (itemId == 286)
		{
			itembasedamage = goldaxe;
		}
		return itembasedamage;
	}

	public int getPlayerDamage(Player player)
	{
		// what are they holding? (if anything)
		int itemId = player.getItemInHand();
		// default base damage is 3 if no item is found
		int damage = getItemDamage(itemId);

		// add melee skill bonus modifier
		damage = damage + getPlayerMelee(player);

		// randomise damage from 1 to max so far
		Random generator = new Random();
		int index = generator.nextInt(damage);

		// add one just incase it was 0 (maybe do a miss here instead)
		index = index + 1;

		return index;
	}
	
	public boolean onBlockDestroy(Player player, Block block)
	{
		if (block.getType() == 81 && player.getItemInHand() == -1)
		{
			if (getPlayerHP(player) < 2)
			{
				player.sendMessage("You commit suicide by cactus! (HP: 0)");
				DoPlayerDeath(player);
			} else {
				setPlayerHP(player, getPlayerHP(player) - 1);
				player.sendMessage("You hurt yourself punching a cactus! (HP: " + getPlayerHP(player) + ")");
			}
		}
		return false;
	}

	public void onArmSwing(Player player)
	{

		// Player trying to hit player player
		for (Player p : etc.getServer().getPlayerList())
		{
			if (p != null) {
				if (p.getName().equals(player.getName()) == true)
				{
				} else {
					if (pvp == true)
					{
						double dist = getPlayerDistance(player, p);
						if (dist <= 3D)
						{
							if (PlayerHasHit(player) == 0)
							{ 
								// missed
								if (getPlayerHP(p) < 1)
								{
									// do nothing they are already dead
								} else {
									player.sendMessage("You try to strike " + p.getName() + " but miss! (HP: " + getPlayerHP(player) + ")");
								}
							} else {
								// hit
								// Get player damage
								int thisdmg = getPlayerDamage(player);

								player.sendMessage("You strike " + p.getName() + " for " + thisdmg + " damage. (HP: " + getPlayerHP(player) + ")");
								if (getPlayerHP(p) < thisdmg)
								{
									player.sendMessage("You have slain " + p.getName() + "!");
									p.sendMessage("You have been slain by " + player.getName() + "!");
									// reset hp and warp to spawn
									DoPlayerDeath(p);
								} else {
									setPlayerHP(p,getPlayerHP(p) - thisdmg);
									p.sendMessage("You have been hit by " + player.getName() + " for " + thisdmg + " damage. (HP: " + getPlayerHP(p) + ")");
								}
							}
						} else {
							// too far away
						}
					}
				}
			}
		}

		// against npc  
		for (Mob m : etc.getServer().getMobList())
		{
			if (m != null) {
				double dist = getDistance(player, m);

				if (dist < 3D)
				{
					if (PlayerHasHit(player) == 0)
					{
						// Missed
						if (m.getHealth() < 1)
						{
							// do nothing they are already dead...
						} else {

							// tell them they missed
							player.sendMessage("You try to strike a " + m.getName() + " but miss! (HP: " + getPlayerHP(player) + ")");
						}
					} else {
						// Hit

						if (m.getHealth() < 1)
						{
							// do nothing they are already dead
						} else {

							// Get player damage
							int thisdmg = getPlayerDamage(player);
							
							player.sendMessage("You strike a " + m.getName() + " for " + thisdmg + " damage. (HP: " + getPlayerHP(player) + ")");
							if (m.getHealth() <= thisdmg)
							{
								player.sendMessage("You have slain a " + m.getName() + "!");
								if (m.getName().equals("Zombie") == true)
								{
									GiveExperience(player,80);
								} else {
									if (m.getName().equals("Skeleton") == true)
									{
										GiveExperience(player,100);
									} else {
										if (m.getName().equals("Creeper") == true)
										{
											GiveExperience(player,200);
										} else {
											if (m.getName().equals("Spider") == true)
											{
												GiveExperience(player,50);
											}
										}
									}
								}
								m.setHealth(0);
							} else {
								m.setHealth(m.getHealth() - thisdmg);
							}
						}
					}
				}
			}
		}
	}
}
