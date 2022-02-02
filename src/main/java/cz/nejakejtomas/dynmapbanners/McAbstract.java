package cz.nejakejtomas.dynmapbanners;

import net.minecraft.util.DyeColor;

import java.util.Arrays;
import java.util.stream.Stream;

public class McAbstract
{
	public static Stream<String> getDyeColorNames() {
		return Arrays.stream(DyeColor.values()).map(DyeColor::getName);
	}
}
