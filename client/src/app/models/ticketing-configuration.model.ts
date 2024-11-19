export interface TicketingConfiguration {
    totalTickets: number;
    ticketReleaseRate: number;
    customerRetrievalRate: number;
    maxTicketCapacity: number;
    availableTickets: number;
    status: string;
  }