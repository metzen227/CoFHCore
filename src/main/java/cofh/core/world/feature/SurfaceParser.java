package cofh.core.world.feature;

import cofh.core.world.FeatureParser;
import cofh.lib.util.WeightedRandomBlock;
import cofh.lib.world.feature.FeatureBase;
import cofh.lib.world.feature.FeatureBase.GenRestriction;
import cofh.lib.world.feature.FeatureGenSurface;
import cofh.lib.world.feature.FeatureGenTopBlock;
import com.google.gson.JsonObject;
import net.minecraft.init.Blocks;
import net.minecraft.world.gen.feature.WorldGenerator;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SurfaceParser extends UniformParser {

	@Override
	protected List<WeightedRandomBlock> generateDefaultMaterial() {

		return Arrays.asList(new WeightedRandomBlock(Blocks.STONE, -1), new WeightedRandomBlock(Blocks.DIRT, -1), new WeightedRandomBlock(Blocks.GRASS, -1), new WeightedRandomBlock(Blocks.SAND, -1), new WeightedRandomBlock(Blocks.GRAVEL, -1), new WeightedRandomBlock(Blocks.SNOW, -1), new WeightedRandomBlock(Blocks.AIR, -1), new WeightedRandomBlock(Blocks.WATER, -1));
	}

	@Override
	protected FeatureBase getFeature(String featureName, JsonObject genObject, WorldGenerator gen, int numClusters, GenRestriction biomeRes, boolean retrogen, GenRestriction dimRes, Logger log) {

		// TODO: WorldGeneratorAdv that allows access to its material list
		List<WeightedRandomBlock> matList = defaultMaterial;
		if (genObject.has("material")) {
			matList = new ArrayList<WeightedRandomBlock>();
			if (!FeatureParser.parseResList(genObject.get("material"), matList, false)) {
				log.warn("Invalid material list! Using default list.");
				matList = defaultMaterial;
			}
		}
		if (genObject.has("followTerrain") && genObject.get("followTerrain").getAsBoolean()) {
			return new FeatureGenTopBlock(featureName, gen, matList, numClusters, biomeRes, retrogen, dimRes);
		} else {
			return new FeatureGenSurface(featureName, gen, matList, numClusters, biomeRes, retrogen, dimRes);
		}
	}

}
