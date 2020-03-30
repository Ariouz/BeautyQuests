package fr.skytasul.quests.requirements;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;

import fr.skytasul.quests.api.requirements.AbstractRequirement;
import fr.skytasul.quests.api.requirements.RequirementCreationRunnables;
import fr.skytasul.quests.editors.Editor;
import fr.skytasul.quests.editors.TextEditor;
import fr.skytasul.quests.editors.TextListEditor;
import fr.skytasul.quests.gui.creation.RequirementsGUI;
import fr.skytasul.quests.utils.Lang;
import fr.skytasul.quests.utils.Utils;

public class PermissionsRequirement extends AbstractRequirement {

	public List<String> permissions = new ArrayList<>();
	public String message = null;
	
	public PermissionsRequirement() {
		super("permissionRequired");
	}

	public boolean test(Player p) {
		for (String perm : permissions){
			if (!p.hasPermission(perm)) return false;
		}
		return true;
	}
	
	public void sendReason(Player p){
		if (message != null) Utils.IsendMessage(p, message, true);
	}

	
	protected void save(Map<String, Object> datas) {
		datas.put("permissions", permissions);
		if (message != null) datas.put("message", message);
	}
	
	protected void load(Map<String, Object> savedDatas) {
		permissions = (List<String>) savedDatas.get("permissions");
		if (savedDatas.containsKey("message")) message = (String) savedDatas.get("message");
	}

	public static class Creator implements RequirementCreationRunnables {
		public void itemClick(Player p, Map<String, Object> datas, RequirementsGUI gui) {
			if (!datas.containsKey("perms")) datas.put("perms", new ArrayList<String>());
			Lang.CHOOSE_PERM_REQUIRED.send(p);
			Editor.enterOrLeave(p, new TextListEditor(p, (obj) -> {
				Lang.CHOOSE_PERM_REQUIRED_MESSAGE.send(p);
				new TextEditor(p, (text) -> {
					datas.put("msg", text);
					gui.reopen(p, false);
				}, () -> {
					gui.reopen(p, false);
				}, () -> {
					datas.put("msg", null);
					gui.reopen(p, false);
				}).enterOrLeave(p);
			}, (List<String>) datas.get("perms")));
		}

		public AbstractRequirement finish(Map<String, Object> datas) {
			PermissionsRequirement req = new PermissionsRequirement();
			req.permissions = (List<String>) datas.get("perms");
			req.message = (String) datas.get("msg");
			return req;
		}

		public void edit(Map<String, Object> datas, AbstractRequirement requirement) {
			datas.put("perms", new ArrayList<>(((PermissionsRequirement) requirement).permissions));
			datas.put("msg", ((PermissionsRequirement) requirement).message);
		}
	}

}
