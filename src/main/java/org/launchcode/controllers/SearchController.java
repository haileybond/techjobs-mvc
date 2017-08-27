package org.launchcode.controllers;

import org.launchcode.models.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping("search")
public class SearchController {

    @RequestMapping(value = "")
    public String search(Model model) {
        model.addAttribute("columns", ListController.columnChoices);
        return "search";
    }

    // TODO #1 - Create handler to process search request and display results
    //display to /results
    @RequestMapping(value = "results")
    // Use @RequestParam to get searchTerm + searchType from form results
    public String search(Model model, @RequestParam String searchTerm, @RequestParam String searchType) {

        // initialize empty error
        String error = "";

        // initialize empty jobList
        ArrayList<HashMap<String, String>> jobList;

        // if searchTerm == All
        if (searchType.equals("all")) {

            // search all of jobdata
            jobList = JobData.findByValue(searchTerm);

        // else, the user chose a searchType
        } else {

            //search by searchTerm and searchType
            jobList = JobData.findByColumnAndValue(searchType, searchTerm);
        }

        // if jobList is empty, pass an error message
        if (jobList.isEmpty()) {

                // add error message
                error = "There are no jobs that meet your search terms. Please try again.";
            }

        // need to pass search results and the ListController as is passed above, and error!
        model.addAttribute("columns", ListController.columnChoices);
        model.addAttribute( "error", error);
        model.addAttribute("jobList", jobList);
        model.addAttribute("searchType", searchType);

        //return "/search"
        return "search";
    }
}
