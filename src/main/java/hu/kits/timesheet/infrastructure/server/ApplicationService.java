package hu.kits.timesheet.infrastructure.server;

import hu.kits.timesheet.domain.TimesheetRepository;
import hu.kits.timesheet.domain.roster.RosterRepository;
import hu.kits.timesheet.usermanagement.Authenticator;

public class ApplicationService {

    public final Authenticator authenticator;
    
    public final TimesheetRepository timesheetRepository;
    
    public final RosterRepository rosterRepository;

    public ApplicationService(Authenticator authenticator, TimesheetRepository timesheetRepository, RosterRepository rosterRepository) {
        this.authenticator = authenticator;
        this.timesheetRepository = timesheetRepository;
        this.rosterRepository = rosterRepository;
    }
    
}
