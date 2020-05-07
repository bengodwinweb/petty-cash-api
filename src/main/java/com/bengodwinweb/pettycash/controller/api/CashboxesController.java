package com.bengodwinweb.pettycash.controller.api;

import com.bengodwinweb.pettycash.dto.model.BoxDto;
import com.bengodwinweb.pettycash.dto.model.CashboxDto;
import com.bengodwinweb.pettycash.dto.response.CashboxResponse;
import com.bengodwinweb.pettycash.dto.response.Response;
import com.bengodwinweb.pettycash.exception.NotFoundException;
import com.bengodwinweb.pettycash.exception.UnauthorizedException;
import com.bengodwinweb.pettycash.exception.ValidationException;
import com.bengodwinweb.pettycash.service.BoxService;
import com.bengodwinweb.pettycash.service.CashboxService;
import com.bengodwinweb.pettycash.util.BoxUtil;
import com.bengodwinweb.pettycash.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
public class CashboxesController {

    @Autowired
    private BoxService boxService;

    @Autowired
    private CashboxService cashboxService;

    @Autowired
    private UserUtil userUtil;

    @GetMapping("/cashboxes")
    public Response<Object> getCashboxes(Principal principal) {
        List<CashboxDto> cashboxes = cashboxService.getCashboxesByUser(principal.getName());
        Response.PageMetaData meta = new Response.PageMetaData(cashboxes.size(), cashboxes.size(), 1, 1);
        return Response.ok().setPayload(cashboxes).setMetadata(meta);
    }

    @PostMapping("/cashboxes")
    public Response<Object> createCashbox(Principal principal, @RequestBody @Valid CashboxDto cashboxDto, BindingResult result, WebRequest req, Errors errors) throws ValidationException {
        if (result.hasErrors()) throw new ValidationException("Error creating new Cashbox", errors);
        return Response.ok().setPayload(cashboxService.createCashbox(cashboxDto, principal.getName()));
    }

    @GetMapping("/cashboxes/{id}")
    public Response<Object> getCashbox(@PathVariable("id") String id, Principal principal) throws NotFoundException, UnauthorizedException {
        if (unauthorized(principal.getName(), id)) throw new UnauthorizedException("Not authorized to view cashbox " + id);
        return Response.ok().setPayload(new CashboxResponse()
                .setCashbox(cashboxService.getCashboxById(id))
                .setCurrentBox(cashboxService.getCurrentBox(id))
                .setChangeBox(cashboxService.getChangeBox(id))
                .setIdealBox(cashboxService.getIdealBox(id))
                .setTransactions(cashboxService.getTransactions(id)));
    }

    @PutMapping("/cashboxes/{id}")
    public Response<Object> updateCashbox(@PathVariable("id") String id, Principal principal, @RequestBody @Valid CashboxDto cashboxToUpdate, BindingResult result, WebRequest req, Errors errors) throws ValidationException, NotFoundException, UnauthorizedException {
        if (unauthorized(principal.getName(), cashboxToUpdate.getId())) throw new UnauthorizedException("Not authorized to update cashbox " + id);
        cashboxService.updateCashbox(cashboxToUpdate);
        return Response.ok().setPayload(new CashboxResponse()
                .setCashbox(cashboxService.getCashboxById(id))
                .setCurrentBox(cashboxService.getCurrentBox(id))
                .setChangeBox(cashboxService.getCurrentBox(id))
                .setIdealBox(cashboxService.getIdealBox(id))
                .setTransactions(cashboxService.getTransactions(id)));
    }

    @PutMapping("/cashboxes/{id}/currentBox")
    public Response<Object> updateCurrentBox(@PathVariable("id") String id, Principal principal, @RequestBody @Valid BoxDto currentBoxToUpdate, BindingResult result, WebRequest req, Errors errors) throws ValidationException, NotFoundException, UnauthorizedException {
        if (unauthorized(principal.getName(), id)) throw new UnauthorizedException("Not authorized to edit cashbox " + id);
        if (result.hasErrors()) throw new ValidationException("Error validating currentBox", errors);
        BoxDto currentBoxDto = cashboxService.getCurrentBox(id);
        if (BoxUtil.calculateBoxDtoTotal(currentBoxToUpdate) != currentBoxDto.getBoxTotal()) throw new ValidationException("Current Box total must match previous total");
        boxService.updateBox(currentBoxDto);
        cashboxService.updateBoxes(id);
        return getCashbox(id, principal);
    }

    @PutMapping("/cashboxes/{id}/changeBox")
    public Response<Object> updateChangeBox(@PathVariable("id") String id, Principal principal, @RequestBody @Valid BoxDto changeBoxToUpdate, BindingResult result, WebRequest req, Errors errors) throws ValidationException, NotFoundException, UnauthorizedException {
        if (unauthorized(principal.getName(), id)) throw new UnauthorizedException("Not authorized to edit cashbox " + id);
        if (result.hasErrors()) throw new ValidationException("Error validating changeBox", errors);
        BoxDto changeBoxDto = cashboxService.getChangeBox(id);
        if (BoxUtil.calculateBoxDtoTotal(changeBoxToUpdate) != changeBoxDto.getBoxTotal()) throw new ValidationException("Change Box total must match previous total");
        boxService.updateBox(changeBoxDto);
        cashboxService.updateBoxes(id);
        return getCashbox(id, principal);
    }

    @PutMapping("/cashboxes/{id}/idealBox")
    public Response<Object> updateIdealBox(@PathVariable("id") String id, Principal principal, @RequestBody @Valid BoxDto idealBoxToUpdate, BindingResult result, WebRequest req, Errors errors) throws ValidationException, NotFoundException, UnauthorizedException {
        if (unauthorized(principal.getName(), id)) throw new UnauthorizedException("Not authorized to edit cashbox " + id);
        if (result.hasErrors()) throw new ValidationException("Error validating idealBox", errors);
        BoxDto idealBoxDto = cashboxService.getIdealBox(id);
        if (BoxUtil.calculateBoxDtoTotal(idealBoxToUpdate) != idealBoxDto.getBoxTotal()) throw new ValidationException("Ideal Box total must match previous total");
        boxService.updateBox(idealBoxDto);
        cashboxService.updateBoxes(id);
        return getCashbox(id, principal);
    }

    @DeleteMapping("/cashboxes/{id}")
    public Response<Object> deleteCashbox(@PathVariable("id") String id, Principal principal) throws NotFoundException, UnauthorizedException {
        if (unauthorized(principal.getName(), id)) throw new UnauthorizedException("Not authorized to delete cashbox " + id);
        return Response.ok().setPayload(cashboxService.deleteCashboxById(id));
    }

    private boolean unauthorized(String principalEmail, String cashboxId) throws NotFoundException {
        return !userUtil.isAdmin(principalEmail) && !cashboxService.userOwnsCashbox(cashboxId, principalEmail);
    }

}
