package code.elix_x.coremods.antiidconflict;

import java.io.File;
import java.util.Map;

import code.elix_x.coremods.antiidconflict.core.AntiIdConflictTransformer;
import code.elix_x.coremods.antiidconflict.core.AntiIdConflictTranslator;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.MCVersion;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.Name;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.SortingIndex;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;

@Name(value = "antiidconflict")
@TransformerExclusions(value = "code.elix_x.coremods")
@MCVersion(value = "1.7.10")
public final class AntiIdConflictCore implements IFMLLoadingPlugin{

	public static final String Transformer = AntiIdConflictTransformer.class.getName();
//	public static final String Transformer = "code.elix_x.coremods.antiidconflict.AntiIdConflictTransformer";
	
	public static final String[] transformers = new String[]{Transformer};
	
	public static File mcDir;
	
	@Override
	public String[] getASMTransformerClass() {
//		return new String[]{};
		return transformers;
	}

	@Override
	public String getModContainerClass() {
//		return "code.elix_x.coremods.antiidconflict.AntiIdConflictBase";
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
