# README #

### What is this repository for? ###

The University IT department needs a simulation tool to model the behaviour of the computing platform and to establish good accounting practices.
The policy constraints are:
- There are at least four different job queues:
  1. Short, interactive jobs that can take up to 2 nodes for no more than 1 hour; a certain subset (say 10%) of the machine must be reserved for this queue;  
  2. Medium-sized jobs that can take up to 10% of the total number of cores, and can last up to 8 hours; another subset (say 30%) of the machine must be reserved for this queue  
  3. Large jobs, that can take up to 16 hours and up to 50% of the total core count;  
  4. Huge, active only from 0500pm Friday to 0900am Monday, where the jobs can potentially reserve the whole machine. During these times the other job queues do not serve requests.  
- Each job queue has associated a cost for number of machine-hours requested.  
- Each job will request a certain number of processor cores for a certain amount of time. For the sake of simplicity you can assume that, at any given time, the queueing software will grant access under a first-come first-served basis, up to the available capacity of the machine.
- At the end of the week, there will be a cutoff time such that no new jobs will start if their estimated completion time will go beyond the end of the work week (thereby leaving the machine free for the weekend queue).

The machine can be assumed to have a constant operational cost per hour. Users are modeled as producers of requests, with each user having a certain budget; each user will make requests up to his available budget. The distribution of user requests should provide examples of jobs in all categories. The number of users can be varied from one simulation run to the next, as well as their budget; there can be mixes of classes of small, medium, and big users. Each user will produce requests; the time between any two successive job requests and the size of the request (within the user class) shall be modeled by an exponential probability distribution, with parameters dependent on the class of user.

The output from the simulation should include
- The number of jobs processed in each queue (throughput) per week;
- The actual number of machine-hours consumed by user jobs;
- The resulting price paid by the users;
- The average wait time in each queue;
- The average turnaround time ratio, i.e. the time from placing the job request to completion of the job divided by the actual runtime of the job;
- The economic balance of the centre, calculated by subtracting from the actual price the operating costs.

### Who do I talk to? ###

Mateusz Gasior mr.nimelo@gmail.com
