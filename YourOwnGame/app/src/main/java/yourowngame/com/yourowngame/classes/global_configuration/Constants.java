package yourowngame.com.yourowngame.classes.global_configuration;

import yourowngame.com.yourowngame.classes.annotations.Delete;

/**
 * Constants interfaces are referencing NOT translateable (global) values [e.g. numbers, auth-tokens, arbitrary strings etc.].
 * If a string can be translated (sensefully) then place it into Strings.xml Resource file!
 *
 * Please group constants within superior Interface 'Constants' by additional interfaces [e.g. Constants > GameObject > {values}]
 *
 * Constants always in UPPER_CASE separated by dash
 *
 * Im a slightly unhappy about the Constants Interface, because Interfaces should be used for
 * inheritance and if this game gets further, the most classes depend on that special "interface"
 * which is basically just a placeholder for constants...
 *
 * Cohesion of other classes will suffer!
 *
 */

public interface Constants {
    interface Developers {
        String WSDT = "Kevin Riedl (WSDT)";
        String SOLUTION = "Christof Jori (SOLUTION)";
    }

    @Delete (
            createdBy = Developers.WSDT,
            description = "Please verify and delete.",
            lastModified = "27.03.2018 21:50"
    )
    interface GameLogic {
        interface GameView {
            float widthInPercentage = 0.35f; //this values is used in player.class (todo: we are sure this values is used correctly?)
            float heightInPercentage = 0.35f;
        }
    }
}
