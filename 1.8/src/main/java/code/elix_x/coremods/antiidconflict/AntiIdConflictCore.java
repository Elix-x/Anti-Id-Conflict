package code.elix_x.coremods.antiidconflict;

import java.io.File;
import java.util.Map;

import code.elix_x.coremods.antiidconflict.core.AntiIdConflictTransformer;
import code.elix_x.coremods.antiidconflict.core.AntiIdConflictTranslator;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.MCVersion;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.Name;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;

@Name(value = "antiidconflict")
@TransformerExclusions(value = "code.elix_x.coremods")
@MCVersion(value = "1.8")
public final class AntiIdConflictCore implements IFMLLoadingPlugin{

	public static final String Transformer = AntiIdConflictTransformer.class.getName();
	
	public static final String[] transformers = new String[]{Transformer};
	
	public static File mcDir;
	
	@Override
	public String[] getASMTransformerClass() {
		return transformers;
	}

	@Override
	public String getModContainerClass() {
		return null;
	}

	@Override
	public String getSetupClass() {
		return AntiIdConflictTranslator.class.getName();
	}

	@Override
	public void injectData(Map<String, Object> data) {
		mcDir = (File) data.get("mcLocation");
	}

	@Override
	public String getAccessTransformerClass() {
		return null;
	}

}
