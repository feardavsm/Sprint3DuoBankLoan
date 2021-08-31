// Inserting The Randomly Created Credentials Into Loan Mortagage MySQL Database
        String query = "INSERT INTO loan.tbl_mortagage (realtor_status, realtor_info, loan_officer_status, purpose_loan, " +
                "est_purchase_price, down_payment, down_payment_percent, total_loan_amount, src_down_payment, add_fund_available, " +
                "apply_co_borrower, b_firstName, b_middleName, b_lastName, b_suffix, b_email, b_dob, b_ssn, b_marital, " +
                "b_cell, b_home, c_firstName, c_middleName, c_lastName, c_suffix, c_email, c_dob, c_ssn, c_marital, c_cell, " +
                "c_home, rent_own_status, monthly_rental_payment, first_mortagage_total_payment, employer_name, position, city, " +
                "state, start_date, end_date, current_job, co_employer_name, co_position, co_city, co_state, co_start_date, co_end_date, " +
                "co_current_job, gross_monthly_income, monthly_over_time, monthly_bonuses, monthly_commision, monthly_dividents, " +
                "c_gross_monthly_income, c_monthly_over_time, c_monthly_bonuses, c_monthly_commision, c_monthly_dividents, " +
                "add_belong, income_source, amount, eConsent_declarer, eConsent_declarer_FirstName, eConsent_declarer_LastName, " +
                "eConsent_declarer_Email, created_on, modified_on, loan_status, user_id)" +
                "values" +
                "('1', '"+expectedRetailerFullName+", "+expectedRetailEmail+"', '1', 'Purchase a Home', '"+expectedEstPurchasePrice+"', " +
                "'"+expectedDownPaymentAmount+"', '"+expectedDownPaymentPercentage+"', '"+expectedTotalLoanAmount+"', " +
                "'Checking/Savings (most recent bank statement)', '', '2', '"+expectedFirstName+"', '', '"+expectedLastName+"', '', " +
                "'"+expectedEmail+"', '', '"+expectedSsn+"', '"+expectedMaritalStatus+"', '"+expectedCell+"', '', '', '', '', '', '', '', '', '', '', '', 'Rent', " +
                "'"+expectedMonthlyRentalPayment+"', '', '"+expectedEmployeeName+"', '', '', '', '', '', '', '', '', '', '', '', '', '', " +
                "'"+expectedGrossMonthlyIncome+"', '', '', '', '', '', '', '', '', '', '', '', '', '', '"+expectedFirstName+"', '"+expectedLastName+"', " +
                "'"+expectedEmail+"', '"+expectedDate+"', '', '', '"+expectedUserId+"')";

        // Updating the MySQL Query Database
        DataBaseUtility.updateQuery(query);



// Syntax to update certain column in the db
String expectedName = "Realtors New Name & Email";
        String realtorInfo = "UPDATE tbl_mortagage SET realtor_info='"+expectedName+"' where id='314'";
        // Send update query to mortagage table under user ID 314 and changes it from actual to expected
        DataBaseUtility.updateQuery(realtorInfo);



// syntax to verify for any duplicates in the db
List<List<Object>> lisOflists = DataBaseUtility.getQueryResultAsListOfLists("select b_email, count(*) from loan.tbl_mortagage group by b_email having count(*)>1");

        // If the list is empty test passes

        Assert.assertFalse(lisOflists.isEmpty(), "The list is not empty, its size is "  + lisOflists.size());
        System.out.println(lisOflists);
