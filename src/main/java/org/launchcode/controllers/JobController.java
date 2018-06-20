package org.launchcode.controllers;

import jdk.nashorn.internal.scripts.JO;
import org.launchcode.models.*;
import org.launchcode.models.data.JobFieldData;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.util.ArrayList;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, HttpServletRequest request) {

        // TODO #1 - get the Job with the given ID and pass it into the view
        String sId = request.getParameter("id");
        Integer id = Integer.parseInt(sId);
        Job job = jobData.findById(id);
        model.addAttribute("job", job);
        return "job-detail";
    }

    @RequestMapping(value = "", method = RequestMethod.POST)   //this path isn't job?id=17
    public String post(Model model, @PathVariable int id) {
        //if no id is passed will this break?
        Job aJob = jobData.findById(id);
        model.addAttribute("job", aJob);
        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @ModelAttribute @Valid JobForm jobForm,
                      Errors errors) {
        if (errors.hasErrors()) {
                model.addAttribute(jobForm);
                return "new-job";
        }

        int employerId = jobForm.getEmployerId();
        int locationId = jobForm.getLocationId();
        int positionTypeId = jobForm.getPositionTypesId();
        int coreId = jobForm.getCoreCompetencyId();

        Job newJob = new Job();
        newJob.setName(jobForm.getName());
        newJob.setEmployer(jobData.getEmployers().findById(employerId));
        newJob.setLocation(jobData.getLocations().findById(locationId));
        newJob.setPositionType(jobData.getPositionTypes().findById(positionTypeId));
        newJob.setCoreCompetency(jobData.getCoreCompetencies().findById(coreId));

        jobData.add(newJob);
        model.addAttribute("job", newJob);
        model.addAttribute("id", newJob.getId());
        return "redirect:/job?id="+ newJob.getId();

    }
}



//             1) GET THE DATA FROM THE VIEW
//             2) GET THE DATA USING THE ID'S
//             3) PASS BOTH TO THE CONSTRUCTOR
//             4) PASS THE NEW OBJECT TO THE NEW VIEW.


        // TODO #6 - Validate the JobForm model, and if valid, create a new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.



