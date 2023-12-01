package use_case.update_restrictions;

import entities.UserFactory;
import entities.User;
import entities.UserDietaryPreferences;

import java.util.Set;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class UpdateRestrictionsInteractor implements UpdateRestrictionsInputBoundary {
    final UpdateRestrictionsDataAccessInterface urDataAccessInterface;
    final UpdateRestrictionsOutputBoundary urOutputBoundary;
    final UserDietaryPreferences user;
    final UserFactory userfactory;

    public UpdateRestrictionsInteractor(UpdateRestrictionsDataAccessInterface urDataAccessInterface,
                                        UpdateRestrictionsOutputBoundary urOutputBoundary,
                                        UserDietaryPreferences user, UserFactory userfactory) {
        this.urDataAccessInterface = urDataAccessInterface;
        this.urOutputBoundary = urOutputBoundary;
        this.user = user;
        this.userfactory = userfactory;
    }
    @Override
    public void execute(UpdateRestrictionsInputData updateRestrictionsInputData) {
        String restriction = updateRestrictionsInputData.getRestriction();
        Float value = updateRestrictionsInputData.getValue();
        User user1 = userfactory.create(user.getRestrictionMap());

        Set<String> restrictedRestrictions = new HashSet<>(Arrays.asList(
                "ketogenic", "vegan", "vegetarian", "maxprotein", "minprotein",
                "maxcarbs", "mincarbs", "maxfat", "minfat", "maxcals", "mincals"));

        Set<String> dietTypes = new HashSet<>(Arrays.asList("ketogenic", "vegan", "vegetarian"));

        if (restrictedRestrictions.contains(restriction.toLowerCase())) {
            System.out.println("First if statement passed: " + restriction + value);

            if (dietTypes.contains(restriction.toLowerCase())) {
                // Remove the existing restriction and add the new one
                user.removeRestriction(restriction, user.getRestriction(restriction));
                user.addRestriction(restriction, value);
                urOutputBoundary.prepareUpdatedView("Successfully Updated restriction: " + restriction);
                System.out.println("Second if statement passed: " + user.getAllKeys());
                urDataAccessInterface.save(user1);
            } else {
                // Update or add the restriction
                user.removeRestriction(restriction, user.getRestriction(restriction));
                user.addRestriction(restriction, value);
                urOutputBoundary.prepareUpdatedView("Successfully Added Restriction: " + restriction);
                System.out.println("Second if statement passed: " + user.getAllKeys());
                urDataAccessInterface.save(user1);
            }
        } else if (restriction.equals("main menu")) {
            urOutputBoundary.prepareGoBackView(restriction);
        } else {
            // Add the new restriction
            user.addRestriction(restriction, value);
            urOutputBoundary.prepareUpdatedView("Successfully Updated restriction: " + restriction);
            urDataAccessInterface.save(user1);
        }
    }
}
