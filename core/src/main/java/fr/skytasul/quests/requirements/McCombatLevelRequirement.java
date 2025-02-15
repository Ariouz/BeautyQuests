package fr.skytasul.quests.requirements;

import java.util.Map;

import org.bukkit.entity.Player;

import fr.skytasul.quests.api.requirements.AbstractRequirement;
import fr.skytasul.quests.api.requirements.TargetNumberRequirement;
import fr.skytasul.quests.utils.ComparisonMethod;
import fr.skytasul.quests.utils.Lang;
import fr.skytasul.quests.utils.compatibility.DependenciesManager;
import fr.skytasul.quests.utils.compatibility.McCombatLevel;
import fr.skytasul.quests.utils.compatibility.MissingDependencyException;

public class McCombatLevelRequirement extends TargetNumberRequirement {

	public McCombatLevelRequirement(){
		this(0, ComparisonMethod.GREATER_OR_EQUAL);
	}
	
	public McCombatLevelRequirement(double target, ComparisonMethod comparison) {
		super(target, comparison);
		if (!DependenciesManager.mmo.isEnabled()) throw new MissingDependencyException("McCombatLevel");
	}

	@Override
	public double getPlayerTarget(Player p) {
		return McCombatLevel.getCombatLevel(p);
	}
	
	@Override
	public void sendReason(Player p){
		Lang.REQUIREMENT_COMBAT_LEVEL.send(p, getFormattedValue());
	}
	
	@Override
	public String getDescription(Player p) {
		return Lang.RDCombatLevel.format(Integer.toString((int) target));
	}
	
	@Override
	public Class<? extends Number> numberClass() {
		return Integer.class;
	}
	
	@Override
	public void sendHelpString(Player p) {
		Lang.CHOOSE_XP_REQUIRED.send(p);
	}
	
	@Override
	public AbstractRequirement clone() {
		return new McCombatLevelRequirement(target, comparison);
	}
	
	@Override
	protected void save(Map<String, Object> datas) {
		super.save(datas);
	}
	
	@Override
	protected void load(Map<String, Object> savedDatas) {
		super.load(savedDatas);
		if (savedDatas.containsKey("level")) super.target = (int) savedDatas.get("level");
	}

}
