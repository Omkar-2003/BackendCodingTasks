**This repository contains solutions for the Technical Assessment. The following questions have been implemented and explained**

**🔹 Q1: Project-Based Explanation** <br />
      **File**: Q1.txt<br />
      **Content**: A detailed explanation of the Transport Shipment & Delivery Booking System project, including the tech stack, real-world use cases, and insights gained during development. It discusses the approach, challenges faced, and the usage of resources like Stack Overflow.

**🔹 Q2: Data Migration Validation**<br />
       **Class Name: Q2.java**<br />
       **Problem**: Ensure data integrity after migrating a database from local to AWS/GCP.<br />
       **Solution**: Implemented a Java solution that compares datasets in the form of List<Map<String, String>> to ensure that no data is lost or changed during migration.<br />
       **Checkpoints for Validation**<br />
			1) Row Count: Verify that the total number of records in both the original and migrated datasets match.<br />
			2) Field-wise Comparison: Ensure that the data in each field matches between the original and migrated datasets.
			3) Timestamp Comparison: Check that the timestamp for each record falls within an acceptable range.
			4) Missing Records: Ensure that no records are missing in the migrated data.
			
**🔹 Q4: API Rate Limiting** <br />
        **Class Name: Q4.java** <br />
        **Problem**: Enforce a limit of 15 API calls per minute, with a penalty for exceeding the limit.<br />
        **Solution**: Implemented a sliding window rate limiter using timestamps to track API calls. If the limit is exceeded, a penalty is enforced for 60 seconds. Additionally, discussed handling scenarios of higher API call requirements using AWS Load Balancer and token bucket strategies.
