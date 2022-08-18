package cc.glaciyan.vpzod.actions;

import cc.glaciyan.vpzod.ZodGenerator;
import com.vp.plugin.action.VPAction;
import com.vp.plugin.action.VPActionController;

import java.io.File;

public class GenerateZodSchema implements VPActionController {
  @Override
  public void performAction(VPAction vpAction) {
    ZodGenerator.generatedZodSchema(new File("C:\\dev\\pixoid\\pixiv\\schema\\schema.ts"));
  }

  @Override
  public void update(VPAction vpAction) {

  }
}
