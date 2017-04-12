package com.opso.med.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.opso.med.domain.Schedule;
import com.opso.med.service.ScheduleService;
import com.opso.med.web.rest.util.HeaderUtil;
import com.opso.med.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Entity.
 */
@RestController
@RequestMapping("/api")
public class ScheduleResource {

    private final Logger log = LoggerFactory.getLogger(ScheduleResource.class);

    private static final String ENTITY_NAME = "schedule";

    private final ScheduleService scheduleService;

    public ScheduleResource(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    /**
     * POST  /schedules : Create a new schedule.
     *
     * @param schedule the schedule to create
     * @return the ResponseEntity with status 201 (Created) and with body the new schedule, or with status 400 (Bad Request) if the schedule has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/schedules")
    @Timed
    public ResponseEntity<Schedule> createSchedule(@RequestBody Schedule schedule) throws URISyntaxException {
        log.debug("REST request to save Schedule : {}", schedule);
        if (schedule.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new schedule cannot already have an ID")).body(null);
        }
        Schedule result = scheduleService.save(schedule);
        return ResponseEntity.created(new URI("/api/schedules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /schedules : Updates an existing schedule.
     *
     * @param schedule the schedule to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated schedule,
     * or with status 400 (Bad Request) if the schedule is not valid,
     * or with status 500 (Internal Server Error) if the schedule couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/schedules")
    @Timed
    public ResponseEntity<Schedule> updateSchedule(@RequestBody Schedule schedule) throws URISyntaxException {
        log.debug("REST request to update Schedule : {}", schedule);
        if (schedule.getId() == null) {
            return createSchedule(schedule);
        }
        Schedule result = scheduleService.save(schedule);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, schedule.getId().toString()))
            .body(result);
    }

    /**
     * GET  /schedules : get all the schedules.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of schedules in body
     */
    @GetMapping("/schedules")
    @Timed
    public ResponseEntity<List<Schedule>> getAllSchedules(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Schedules");
        Page<Schedule> page = scheduleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/schedules");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


    /**
     * GET  /schedules/expert/{expertId} : get all the schedules of an expert.
     *
     * @param expertId the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of schedules in body
     */
    @GetMapping("/schedules/expert/{expertId}")
    @Timed
    public ResponseEntity<List<Schedule>> findByExpertId(@PathVariable String expertId) {
        log.debug("REST request to get a page of Schedules");
        List<Schedule> schedules = scheduleService.findByExpertId(expertId);
        return new ResponseEntity<>(schedules, HttpStatus.OK);
    }


    /**
     * GET  /schedules/office/{officeId} : get all the schedules of an expert.
     *
     * @param officeId the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of schedules in body
     */
    @GetMapping("/schedules/office/{officeId}")
    @Timed
    public ResponseEntity<List<Schedule>> findByOfficeId(@PathVariable String officeId) {
        log.debug("REST request to get a page of Schedules");
        List<Schedule> schedules = scheduleService.findByOfficeId(officeId);
        return new ResponseEntity<>(schedules, HttpStatus.OK);
    }


    /**
     * GET  /schedules/query : get all the schedules of an expert.
     *
     * @param officeId the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of schedules in body
     */
    @GetMapping("/schedules/query")
    @Timed
    public ResponseEntity<List<Schedule>> query(@RequestParam(name = "office", required = false) String officeId, @RequestParam(name = "expert", required = false) String expertId) {
        log.debug("REST request to get a page of Schedules");
        List<Schedule> schedules = scheduleService.findByOffficeIdAndExpertId(officeId,expertId);
        return new ResponseEntity<>(schedules, HttpStatus.OK);
    }

    /**
     * GET  /schedules/:id : get the "id" schedule.
     *
     * @param id the id of the schedule to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the schedule, or with status 404 (Not Found)
     */
    @GetMapping("/schedules/{id}")
    @Timed
    public ResponseEntity<Schedule> getSchedule(@PathVariable String id) {
        log.debug("REST request to get Schedule : {}", id);
        Schedule schedule = scheduleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(schedule));
    }

    /**
     * DELETE  /schedules/:id : delete the "id" schedule.
     *
     * @param id the id of the schedule to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/schedules/{id}")
    @Timed
    public ResponseEntity<Void> deleteSchedule(@PathVariable String id) {
        log.debug("REST request to delete Schedule : {}", id);
        scheduleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
