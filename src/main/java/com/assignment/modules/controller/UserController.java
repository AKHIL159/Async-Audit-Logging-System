package com.assignment.modules.controller;

import com.assignment.modules.dto.UserDTO;
import com.assignment.modules.model.User;
import com.assignment.modules.service.AuditService;
import com.assignment.modules.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuditService auditService;

    @GetMapping
    public CompletableFuture<ResponseEntity<List<UserDTO>>> getAllUsers() {
        return userService.getAllUsers()
                .thenApply(ResponseEntity::ok);
    }

    @GetMapping("/email/{email}")
    public CompletableFuture<ResponseEntity<?>> getUserByEmail(@PathVariable String email, HttpServletRequest request) {
        String dataThreadName = Thread.currentThread().getName();
        return userService.getUserByEmail(email)
                .thenApply(
                user -> {
                    if (user == null) {
                        auditService.logAudit(
                                email,
                                "GET_USER_FAILED",
                                null,
                                "User not found",
                                request,
                                Thread.currentThread().getName(),
                                dataThreadName,
                                404,
                                "No user with given email"
                        );
                        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body("User with email " + email + " not found.");
                    } else {
                        auditService.logAudit(
                                email,
                                "GET_USER",
                                user.getId(),
                                "User fetched successfully",
                                request,
                                Thread.currentThread().getName(),
                                dataThreadName,
                                200,
                                "No error"
                        );
                        return ResponseEntity.ok(user);
                    }
                }
        );
//        return userService.getUserByEmail(email)
//                .thenApply(user -> user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build());
    }

    @PostMapping
    public CompletableFuture<ResponseEntity<?>> createUser(@RequestBody User user, HttpServletRequest request) {
        String dataThreadName = Thread.currentThread().getName();
        return userService.createUser(user)
                .thenApply(createdUser -> {
                    if (createdUser == null) {
                        auditService.logAudit(
                                user.getEmail(),
                                "CREATE_USER_FAILED",
                                null,
                                "FAILED, CHECK ERROR",
                                request,
                                Thread.currentThread().getName(),
                                dataThreadName,
                                400,
                                "User with email " + user.getEmail() + " already exists."
                        );
                        return ResponseEntity.status(HttpStatus.CONFLICT)
                                .body("User with email " + user.getEmail() + " already exists.");
                    } else {
                        auditService.logAudit(
                                user.getEmail(),                    // username
                                "CREATE_USER",                      // action
                                createdUser.getId(),                // entityId
                                "User created successfully",        // details
                                request,                            // HttpServletRequest
                                Thread.currentThread().getName(),   // audit thread name
                                dataThreadName,                     // data thread name
                                201,                                // status code
                                "No error"                          // error message
                        );
                        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
                    }
                });
    }

    @PutMapping("/email/{email}")
    public CompletableFuture<ResponseEntity<?>> updateUser(@PathVariable String email, @RequestBody User user, HttpServletRequest request) {
        String dataThreadName = Thread.currentThread().getName();
        return userService.updateUser(email, user)
            .thenApply(updated -> {
                if (updated != null) {
                    auditService.logAudit(
                            email,
                            "UPDATE_USER",
                            updated.getId(),
                            "User updated successfully",
                            request,
                            Thread.currentThread().getName(),
                            dataThreadName,
                            200,
                            "No error"
                    );
                    return ResponseEntity.status(HttpStatus.OK).body(updated);
                } else {
                    auditService.logAudit(
                            email,
                            "UPDATE_USER_FAILED",
                            null,
                            "User not found",
                            request,
                            Thread.currentThread().getName(),
                            dataThreadName,
                            404,
                            "No user with given email"
                    );
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body("User with email " + email + " not found.");
                }
            });



//        return userService.updateUser(email, user)
//                .thenApply(updatedUser -> updatedUser != null ? ResponseEntity.ok(updatedUser)
//                        : ResponseEntity.notFound().build());
    }

    @DeleteMapping("/email/{email}")
    public CompletableFuture<ResponseEntity<?>> deleteUser(@PathVariable String email, HttpServletRequest request) {
        String dataThreadName = Thread.currentThread().getName();
        return userService.deleteUser(email).thenApply(
                deleted -> {
                    if (deleted != null) {
                        auditService.logAudit(
                                email,
                                "DELETE_USER",
                                deleted.getId(),
                                "User deleted successfully",
                                request,
                                Thread.currentThread().getName(),
                                dataThreadName,
                                202,
                                "No error"
                        );
                        return ResponseEntity.status(HttpStatus.ACCEPTED)
                                .body("User with email " + email + " deleted");
                    } else {
                        auditService.logAudit(
                                email,
                                "DELETE_USER_FAILED",
                                null,
                                "User not found",
                                request,
                                Thread.currentThread().getName(),
                                dataThreadName,
                                404,
                                "No user with given email"
                        );
                        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body("User with email " + email + " not found.");
                    }
                }
        );
//        return userService.deleteUser(email)
//                .thenApply(deleted -> deleted ? ResponseEntity.noContent().build()
//                        : ResponseEntity.notFound().build());
    }


//      -----------Post-DTO------------
//    @PostMapping
//    public ResponseEntity<?> createUser(@RequestBody User user) {
//        User created = userService.createUser(user);
//        if (created == null) {
//            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists.");
//        }
//        return ResponseEntity.ok(created);
//    }
//
//    @GetMapping
//    public ResponseEntity<List<UserDTO>> getAllUsers() {
//        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
//    }
//
//    @GetMapping("/email/{email}")
//    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
//        UserDTO user = userService.getUserByEmail(email);
//        if (user != null) {
//            return new ResponseEntity<>(user, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @PutMapping("/email/{email}")
//    public ResponseEntity<User> updateUser(@PathVariable String email, @RequestBody User user) {
//        User updated = userService.updateUser(email, user);
//        if (updated != null) {
//            return new ResponseEntity<>(updated, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @DeleteMapping("/email/{email}")
//    public ResponseEntity<Void> deleteUser(@PathVariable String email) {
//        boolean deleted = userService.deleteUser(email);
//        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
//
//    }
//      -----------Pre-DTO------------
//    @PostMapping
//    public ResponseEntity<?> createUser(@RequestBody User user) {
//        try {
//            return ResponseEntity.ok(userService.createUser(user));
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
//
//    @GetMapping
//    public List<User> getAllUsers() {
//        return userService.getAllUsers();
//    }
//
//    @GetMapping("/email/{email}")
//    public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
//        return userService.getUserByEmail(email)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }
//
//    @PutMapping("/email/{email}")
//    public ResponseEntity<?> updateUserByEmail(@PathVariable String email, @RequestBody User user) {
//        try {
//            return ResponseEntity.ok(userService.updateUserByEmail(email, user));
//        } catch (NoSuchElementException e) {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    @DeleteMapping("/email/{email}")
//    public ResponseEntity<?> deleteUserByEmail(@PathVariable String email) {
//        userService.deleteUserByEmail(email);
//        return ResponseEntity.noContent().build();
//    }
}
