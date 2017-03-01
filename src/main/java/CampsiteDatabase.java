import java.util.*;

/**
 * Class representing a database of reservations for campsites.
 *
 * @author  Sander Peerna
 * @version 0.1
 * @since   2/24/2017
 */
public class SearchDatabase {
    private static final int DAYS_IN_YEAR = 366;

    private HashMap<Long, String> campsites;        // Assume id's are not 1 -> # of campsites, could be 43299292, ..., 50230203, ...
    private HashMap<Long, boolean[]> reservations;
    private ArrayList<Integer> gapRules;            // Assume there are only a few rules

    /**
     * Class constructor.
     */
    public SearchDatabase() {
        campsites = new HashMap<>();
        reservations = new HashMap<>();
        gapRules = new ArrayList<>();
    }

    /**
     * Maps a campsite to the id, if absent.
     * @param id    the id of the campsite
     * @param name  the name of the campsite
     */
    public void addCampsite(long id, String name) {
        campsites.putIfAbsent(id, name);
    }

    /**
     * Removes a campsite from the map.
     * @param id    the id of the campsite
     */
    public void removeCampsite(long id) {
        campsites.remove(id);
    }

    /**
     * Retrieves a campsite from the map.
     * @param id    the id of the campsite
     * @return      the name of the campsite
     */
    public String getCampsite(long id) {
        return campsites.get(id);
    }

    /**
     * Returns an entire map of campsites.
     * @return the map of campsites
     */
    public HashMap<Long, String> getCampsites() {
        return campsites;
    }

    /**
     * Adds a reservation for a campsite.
     * @param id        the id of the campsite
     * @param startDay  the reservation start day of the year (1-366)
     * @param endDay    the reservation end day of the year (1-366)
     */
    public void addReservation(long id, int startDay, int endDay) {
        boolean[] month = (reservations.get(id) == null) ? new boolean[DAYS_IN_YEAR] : reservations.get(id);

        for (int day = startDay; day <= endDay; day++) {
            month[day] = true;
        }
        reservations.put(id, month);
    }

//    Removes a reservation for a campsite.
//    public void removeReservation() {
//    }

    /**
     * Checks if a campsite is reserved.
     * @param id    the id of the campsite
     * @param day   the day of the year
     * @return      true if the campsite is reserved on the given day of the year, else false
     */
    public boolean isReserved(long id, int day) {
        return reservations.get(id)[day];
    }

    /**
     * Add a new gap size to the set of gap rules.
     * @param gap   the size of the gap
     */
    public void addGapRule(int gap) {
        gapRules.add(gap);
    }

    /**
     * Remove a gap size from the set of gap rules.
     * @param gap   the size of the gap
     */
    public void removeGapRule(int gap) {
        gapRules.remove(gapRules.indexOf(gap));
    }

    /**
     * Gets the largest gap size in the set of gap rules.
     * @return  the size of the largest gap
     */
    public int getMaxGapRule() {
        Collections.sort(gapRules);
        return gapRules.get(gapRules.size() - 1);
    }

    /**
     * Checks if a gap size is in the set of gap rules.
     * @param gap   the size of the gap
     * @return      true if the gap size is in the set of gap rules, else false
     */
    public boolean isGapRule(int gap) {
        return gapRules.contains(gap);
    }
}
