import java.util.Calendar;
import java.util.List;
import java.util.LinkedList;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

public class ContactManagerImpl implements ContactManager {
	private Map<Integer, Contact> idContactsMap;
	private Map<Integer, Meeting> idMeetingsMap;
	private int meetingId;
	
	public ContactManagerImpl() {
		idContactsMap = new HashMap<>();
		idMeetingsMap = new HashMap<>();
		meetingId = 0;
	}
	private void checkContactsAreKnown(Set<Contact> contacts) {
		for (Contact contact : contacts) {
			boolean unknownContact = !idContactsMap.containsValue(contact);//True if does NOT contain contact
			if (unknownContact) {
				throw new IllegalArgumentException(contact.getName() + "is an unknown contact");
			}
		}
	}
	public int addFutureMeeting(Set<Contact> contacts, Calendar date) {
		checkContactsAreKnown(contacts);
		if (date == null || date.compareTo(Calendar.getInstance()) < 0) {
			throw new IllegalArgumentException("Date of meeting to be added should be in the future NOT the past.");
		}
		meetingId++;
		idMeetingsMap.put(meetingId, new FutureMeetingImpl(meetingId, contacts, date));
		return meetingId;
	}
	public PastMeeting getPastMeeting(int id) {
		Meeting pastMeeting = idMeetingsMap.get(id);
		if (pastMeeting == null || pastMeeting.getDate().compareTo(Calendar.getInstance()) < 0) {
			return (PastMeeting) pastMeeting;//Returns null if id is not mapped to meeting
		} else {						// or returns pastMeeting if its date is in the past
			throw new IllegalArgumentException("Date of meeting is in the future.");
		}
	}
	public FutureMeeting getFutureMeeting(int id) {
		Meeting futureMeeting = idMeetingsMap.get(id);
		if (futureMeeting == null || futureMeeting.getDate().compareTo(Calendar.getInstance()) > 0) {
			return (FutureMeeting) futureMeeting;//Returns null if id is not mapped to meeting
		} else {							// or returns pastMeeting if its date is in the future
			throw new IllegalArgumentException("Date of meeting is in the past.");
		}
	}
	public Meeting getMeeting(int id) {
		return idMeetingsMap.get(id);//Returns meeting/value (or null)  mapped to the id/key
	}
	public List<Meeting> getFutureMeetingList(Contact contact) {
		if (!idContactsMap.containsValue(contact)) {
			throw new IllegalArgumentException(contact.getName() + "is an unknown contact");
		}
		List<Meeting> contactFutureMeetings = new LinkedList<>();
		for (Meeting meeting : idMeetingsMap.values()) {
			if (meeting.getContacts().contains(contact) && meeting.getDate().compareTo(Calendar.getInstance()) > 0) {
				contactFutureMeetings.add(meeting);
			}
		}
		Collections.sort(contactFutureMeetings, new DateMeetingComparator());
		return contactFutureMeetings;
	}
	public List<Meeting> getFutureMeetingList(Calendar date) {
		
	}
	public List<PastMeeting> getPastMeetingList(Contact contact) {
		
	}
	public void addNewPastMeeting(Set<Contact> contacts, Calendar date, String text) {
		
	}
	public void addMeetingNotes(int id, String text) {
		
	}
	public void addNewContact(String name, String notes) {
		
	}
	public Set<Contact> getContacts(int... ids) {
		
	}
	public Set<Contact> getContacts(String name) {
		
	}
	public void flush() {
	
	}
}