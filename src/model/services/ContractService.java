package model.services;

import java.util.Calendar;

import model.entities.Contract;
import model.entities.Installment;

public class ContractService {
	
	private OnlinePaymentService onlinePaymentService;
	
	public ContractService(OnlinePaymentService onlinePaymentService) {
		this.onlinePaymentService = onlinePaymentService;
	}
	
	public void processContract(Contract contract, Integer months) {
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(contract.getDate());
		
		for(int i = 1; i <= months; i++) {
			
			double amount = contract.getTotalValue() / months;
			double fee = onlinePaymentService.paymentFee(amount);
			amount += fee + onlinePaymentService.interest(amount, i);
			
			cal.add(Calendar.MONTH, 1);
			
			contract.addInstallment(new Installment(cal.getTime(), amount));
		}
		
	}
}
