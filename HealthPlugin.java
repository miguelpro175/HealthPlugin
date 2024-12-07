import java.util.logging.Logger;
import java.util.logging.Level;
import java.io.IOException;

public class s3HealthPlugin extends Plugin
{
	private String name = "s3HealthPlugin";
	private String version = "1.17";
	
	
	static boolean pvp = false;
	static boolean dropinventory = true;
	static int Combattimer = 1000;
	static int woodensword = 2;
	static int stonesword = 3;
	static int ironsword = 4;
	static int goldsword = 4;
	static int diamondsword = 5;
	static int woodenspade = 1;
	static int stonespade = 2;
	static int ironspade = 3;
	static int goldspade = 3;
	static int diamondspade = 4;
	static int woodenpickaxe = 1;
	static int stonepickaxe = 2;
	static int ironpickaxe = 3;
	static int goldpickaxe = 3;
	static int diamondpickaxe = 4;
	static int woodenaxe = 1;
	static int stoneaxe = 2;
	static int ironaxe = 3;
	static int goldaxe = 3;
	static int diamondaxe = 4;
	static int basedamage = 1;
	static int maxhealth = 10;

	private PropertiesFile properties = new PropertiesFile("s3HealthPlugin.properties");
	    
	static final Logger log = Logger.getLogger("Minecraft");
	static s3HealthListener listener;
	
	public void enable()
	{
		log.info(this.name + " " + this.version + " enabled");
		etc.getInstance().addCommand("/rotation", "- Shows your current rotation.");
		etc.getInstance().addCommand("/health", "- Shows your current health.");
		etc.getInstance().addCommand("/hp", "- Shows your current health.");
		etc.getInstance().addCommand("/pvpenable", "- Enable PVP");
		etc.getInstance().addCommand("/pvpdisable", "- Disable PVP");
		etc.getInstance().addCommand("/heal", "- Sets you to full health");
		loadProperties();
		listener = new s3HealthListener();
		log.info(this.name + " " + this.version + " loaded");
	}
	
	public void loadProperties()
    {
		properties = new PropertiesFile("s3HealthPlugin.properties");
		properties.load();
		try {
			pvp = properties.getBoolean("pvp", false);
			dropinventory = properties.getBoolean("drop-inventory", true);
			Combattimer = properties.getInt("combat-timer", 1000);
			woodensword = properties.getInt("wooden-sword", 2);
			stonesword = properties.getInt("stone-sword", 3);
			ironsword = properties.getInt("iron-sword", 3);
			goldsword = properties.getInt("gold-sword", 4);
			diamondsword = properties.getInt("diamond-sword", 5);
			woodenspade = properties.getInt("wooden-spade", 1);
			stonespade = properties.getInt("stone-spade", 2);
			ironspade = properties.getInt("iron-spade", 3);
			goldspade = properties.getInt("gold-spade", 3);
			diamondspade = properties.getInt("diamond-spade", 4);
			woodenpickaxe = properties.getInt("wooden-pickaxe", 1);
			stonepickaxe = properties.getInt("stone-pickaxe", 2);
			ironpickaxe = properties.getInt("iron-pickaxe", 3);
			goldpickaxe = properties.getInt("gold-pickaxe", 3);
			diamondpickaxe = properties.getInt("diamond-pickaxe", 4);
			woodenaxe = properties.getInt("wooden-axe", 1);
			stoneaxe = properties.getInt("stone-axe", 2);
			ironaxe = properties.getInt("iron-axe", 3);
			goldaxe = properties.getInt("gold-axe", 3);
			diamondaxe = properties.getInt("diamond-axe", 4);
			basedamage = properties.getInt("basedamage", 1);
			maxhealth = properties.getInt("maxhealth", 10);

			log.info(this.name + " " + this.version + " - Properties Loader: pvp=" +this.pvp);
			log.info(this.name + " " + this.version + " - Properties Loader: drop inventory=" + this.dropinventory);
			log.info(this.name + " " + this.version + " - Properties Loader: combat timer=" + this.Combattimer);

		} catch (Exception e) {

		}
		// TODO : non-existant file
		//System.out.println(getDateTime() + " [DEBUG] s3HealthPlugin - Properties Loader: s3healthplugin.properties NOT FOUND!");
		
	}

	public void disable()
	{
		etc.getInstance().removeCommand("/health");
		etc.getInstance().removeCommand("/hp");
		etc.getInstance().removeCommand("/rotation");
		etc.getInstance().removeCommand("/pvpenable");
		etc.getInstance().removeCommand("/pvpdisable");
		etc.getInstance().removeCommand("/heal");
		log.info(this.name + " " + this.version + " disabled");
	}

	public void initialize() 
	{
		etc.getLoader().addListener(PluginLoader.Hook.ARM_SWING, listener, this, PluginListener.Priority.MEDIUM);
		etc.getLoader().addListener(PluginLoader.Hook.LOGIN, listener, this, PluginListener.Priority.MEDIUM);
		etc.getLoader().addListener(PluginLoader.Hook.COMMAND, listener, this, PluginListener.Priority.MEDIUM);
		etc.getLoader().addListener(PluginLoader.Hook.BLOCK_DESTROYED, listener, this, PluginListener.Priority.MEDIUM);
		etc.getLoader().addListener(PluginLoader.Hook.BLOCK_CREATED, listener, this, PluginListener.Priority.MEDIUM);
		etc.getLoader().addListener(PluginLoader.Hook.DISCONNECT, listener, this, PluginListener.Priority.MEDIUM);
	}
}
