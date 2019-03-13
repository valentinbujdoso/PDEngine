package org.rulez.demokracia.pdengine;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.tested_behaviour;
import org.rulez.demokracia.pdengine.annotations.tested_feature;
import org.rulez.demokracia.pdengine.annotations.tested_operation;
import org.rulez.demokracia.pdengine.exception.ReportedException;
import org.rulez.demokracia.pdengine.testhelpers.CreatedDefaultVoteRegistry;

public class ChoiceDeleteAdminKeyIsUser extends CreatedDefaultVoteRegistry {

	public ChoiceDeleteAdminKeyIsUser() {
		super();
	}

	@tested_feature("Manage votes")
	@tested_operation("delete choice")
	@tested_behaviour("if \"user\" is used as adminKey, then the user must be the one who added the choice and canAddIn be true")
	@Test
	public void adminKey_is_user_and_the_user_is_the_same_with_that_user_who_added_the_choice_and_canAddin_is_false() throws ReportedException {
		Vote vote = voteManager.getVote(adminInfo.voteId);
		vote.canAddin = false;
		String choiceId = voteManager.addChoice(adminInfo.adminKey, adminInfo.voteId, "choice1", "test_user_in_ws_context");
		assertThrows(
			() -> voteManager.deleteChoice(adminInfo.voteId, choiceId , "user")
		).assertMessageIs("The adminKey is \"user\" but canAddin is false.");
	}
	
	@tested_feature("Manage votes")
	@tested_operation("delete choice")
	@tested_behaviour("if \"user\" is used as adminKey, then the user must be the one who added the choice and canAddIn be true")
	@Test
	public void adminKey_is_user_and_the_user_is_not_the_same_with_that_user_who_added_the_choice_and_canAddin_is_true() throws ReportedException {
		Vote vote = voteManager.getVote(adminInfo.voteId);
		vote.canAddin = true;
		String choiceId = voteManager.addChoice(adminInfo.adminKey, adminInfo.voteId, "choice1", "user");
		assertThrows(
			() -> voteManager.deleteChoice(adminInfo.voteId, choiceId , "user")
		).assertMessageIs("The adminKey is \"user\" but the user is not same with that user who added the choice.");
	}
	
	
	@tested_feature("Manage votes")
	@tested_operation("delete choice")
	@tested_behaviour("if \"user\" is used as adminKey, then the user must be the one who added the choice and canAddIn be true")
	@Test
	public void adminKey_is_user_and_the_user_is_the_same_with_that_user_who_added_the_choice_and_canAddin_is_true() throws ReportedException {
		String voteId = adminInfo.voteId;
		Vote vote = voteManager.getVote(voteId);
		vote.canAddin = true;
		String choiceId = voteManager.addChoice(adminInfo.adminKey, adminInfo.voteId, "choice1", "test_user_in_ws_context");
		voteManager.deleteChoice(voteId, choiceId, adminInfo.adminKey);
		
		assertThrows(
			() -> voteManager.getChoice(voteId, choiceId)
		).assertMessageIs(String.format("Illegal choiceId: %s", choiceId));
	}
}