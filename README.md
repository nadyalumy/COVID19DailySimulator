# The COVID-19 Daily Simulator
Simulate the spread of COVID-19 in a closed community based on the different variants of the virus using the COVID-19 Daily Simulator!

## What is the COVID-19 Daily Simulator?
The COVID-19 Daily Simulator allows clients to simulate the spread of COVID-19 in a community where clients can control various starting variables. Using this simulator, clients can choose the starting population in the community, including how many persons are initially exposed to COVID-19, infected with COVID-19, and completely non-exposed and non-infected. The COVID-19 daily simulator also takes into account the differences in the spread of COVID-19 based on the different COVID-19 variants, particularly the Alpha, Delta and Omicron variants. Thus, clients can also specify how many persons would be exposed to and infected with each variant in the community before running the simulation.

## Preconditions and Assumptions
The COVID-19 Daily Simulator focuses on how COVID-19 spreads in a community given that some members of the community are either on their first day of exposure to the virus or on their first day of infection. All other persons are non-exposed, non-infected and have never been infected.

It is also important to note that the community used in this simulator is a closed community where its members only interact with one another and not with members from outside the community. Some examples of such communities that this simulator is suitable for are cruise ships or student dormitories (where the students are on lockdown and cannot leave the dormitory).

This simulator does not take into account different age groups and vaccination status. This simulator mainly focuses on the spread of COVID-19 amongst unvaccinated adults. 

Based on a past study, it is found that each person on average comes into close contact with 16 other persons in a day.<sup>1</sup>
  Hence, in our simulator, each human can come into contact with a maximum of 16 other persons in each day.
 
The COVID-19 Daily Simulator works best for periods of up to 280 days. When creating this simulator, I used data related to COVID-19 infections found in published research and many of the research I found were studies that span a period of 40 weeks (280 days). Hence, results of the simulation may decrease in accuracy for periods of beyond 280 days.

Secondary attack rate refers to "the probability that an infection occurs among susceptible people within a specific group (ie, household or close contacts)."<sup>2</sup>  In addition, an incubation period refers to "the time period from the initial exposure to the infectious agent until the appearance of the signs and symptoms of the disease."<sup>3</sup>  In this simulator, to determine whether an exposed human turned infected or not, I used secondary attack rates and incubation periods for each COVID-19 variant that I have found from past research studies. During the simulation, it is assumed that all individuals are not contagious during the incubation period and only turns contagious once they are infected with the virus.

In this simulator, the incubation period is 4.96 days for the Alpha variant, 4.43 days for the Delta variant and 3.61 days for the Omicron variant.<sup>4</sup>  For individuals who have never recovered from COVID-19 (have never been infected), the Alpha variant secondary attack rate is 36.4%.<sup>5</sup>  On the other hand, the secondary attack rate for the Delta and Omicron variants are 58.2% and 80.9% respectively.<sup>6</sup>  Based on the protection given by past COVID-19 infection, for individuals who have previously recovered, the secondary attack rate for the Alpha, Delta and Omicron variants are 9.8%, 8% and 44% respectively.<sup>7</sup>

Throughout the simulation, if a human becomes exposed, it will undergo an incubation period according to the COVID-19 variant that they are exposed to. During each day of the incubation period, the probability of the human turning infected from exposed depends on the specified secondary attack rate. 

## How to Run the Simulation
To initialize and run the simulation, run Simulator.main() in Simulator.java and pass in 8 input arguments of integers that correspond to the initial number of humans exposed to the Alpha variant, initial number of humans exposed to the Delta variant, initial number of humans exposed to the Omicron variant, initial number of humans infected by the Alpha variant, initial number of humans infected by the Delta variant, initial number of humans infected by the Omicron variant, initial number of humans that are non-exposed and non-infected to any variants and lastly, the number of days that the simulation should simulate over.

For example, to initialize a simulation that should simulate a period of 150 days with initially no humans exposed to any of the variants, 3 humans infected with the Alpha variant, 5 humans infected with the Delta variant, 1 human infected with the Omicron variant, and 600 humans that are non-exposed and non-infected, a client should pass in the following arguments: `0 0 0 3 5 1 600 150`.

Once the simulation is run, the program should print out the status of the community at each specified day with the number of infected, exposed and recovered humans at each day.

## References

<sup>1</sup> Del Valle, Sara & Hyman, James & Hethcote, Herbert & Eubank, SG. (2007). Mixing patterns between age groups in social networks. Social Networks. 29. 539-554. 10.1016/j.socnet.2007.04.005. 

<sup>2</sup> Liu, Yang et al. “Secondary attack rate and superspreading events for SARS-CoV-2.” _Lancet (London, England)_ vol. 395,10227 (2020): e47. doi:10.1016/S0140-6736(20)30462-1

<sup>3</sup> Wassie, Gizachew Tadesse Tadesse, et al. “Incubation Period of Severe Acute Respiratory Syndrome Novel Coronavirus 2 That Causes Coronavirus Disease 2019: A Systematic Review and Meta-Analysis.” _Current Therapeutic Research_, Elsevier, 11 Oct. 2020, www.sciencedirect.com/science/article/pii/S0011393X20300333. 

<sup>4</sup> Simon, Galmiche, et al. _SARS-COV-2 Incubation Period across Variants of Concern, Individual Factors, and Circumstances of Infection in France: A Case Series Analysis from the ComCor Study_, www.thelancet.com/journals/lanmic/article/PIIS2666-5247(23)00005-8/fulltext. Accessed 21 Aug. 2023. 

<sup>5</sup> Madewell, Zachary J et al. “Household Secondary Attack Rates of SARS-CoV-2 by Variant and Vaccination Status: An Updated Systematic Review and Meta-analysis.” _JAMA network open_ vol. 5,4 e229317. 1 Apr. 2022, doi:10.1001/jamanetworkopen.2022.9317

<sup>6</sup> López-Muñoz, Israel et al. “SARS-CoV-2 Secondary Attack Rates in Vaccinated and Unvaccinated Household Contacts during Replacement of Delta with Omicron Variant, Spain.” _Emerging infectious diseases_ vol. 28,10 (2022): 1999-2008. doi:10.3201/eid2810.220494

<sup>7</sup> “Covid-19 Infection Can Hit More than Once: NCID.” _National Centre for Infectious Diseases, Singapore_, www.ncid.sg/News-Events/News/Pages/COVID-19-infection-can-hit-more-than-once-NCID.aspx#:~:text=People%20who%20have%20had%20COVID,on%20Tuesday%20(March%208). Accessed 21 Aug. 2023. 
