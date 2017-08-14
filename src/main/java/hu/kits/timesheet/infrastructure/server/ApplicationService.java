package hu.kits.timesheet.infrastructure.server;

import hu.kits.timesheet.domain.TimesheetRepository;
import hu.kits.timesheet.usermanagement.Authenticator;

public class ApplicationService {

    public final Authenticator authenticator;
    
    public final TimesheetRepository timesheetRepository;

    public ApplicationService(Authenticator authenticator, TimesheetRepository timesheetRepository) {
        this.authenticator = authenticator;
        this.timesheetRepository = timesheetRepository;
    }
    
}
