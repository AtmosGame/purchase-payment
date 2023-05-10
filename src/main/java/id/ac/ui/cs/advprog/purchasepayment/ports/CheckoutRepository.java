package id.ac.ui.cs.advprog.purchasepayment.ports;

import id.ac.ui.cs.advprog.purchasepayment.models.Cart;
import id.ac.ui.cs.advprog.purchasepayment.models.Checkout;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CheckoutRepository extends CrudRepository<Checkout, Integer>{
}
