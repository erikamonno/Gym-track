package it.erika.gymtrack.dto;

import lombok.Data;

@Data
public class InvoiceStatisticsDto {

    private Long totalInvoices;

    private Double totalAmount;
}
