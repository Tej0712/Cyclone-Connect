package onetoone.Organizations;

import onetoone.Users.User;
import onetoone.Users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private UserRepository userRepository;

    // Save a new organization
    @Transactional
    public Organization saveOrganization(Organization organization) {
        return organizationRepository.save(organization);
    }

    // Get all organizations
    @Transactional(readOnly = true)
    public List<Organization> getAllOrganizations() {
        return organizationRepository.findAll();
    }

    // Get a single organization by ID
    @Transactional(readOnly = true)
    public Organization getOrganizationById(Long organizationId) {
        return organizationRepository.findById(organizationId).orElse(null);
    }

    public List<Organization> getOrganizationsByUserId(Long userId) {
        return organizationRepository.findOrganizationsByUserId(userId);
    }

    // Update an organization
    @Transactional
    public Organization updateOrganization(Long organizationId, Organization organizationDetails) {
        return organizationRepository.findById(organizationId)
                .map(organization -> {
                    organization.setName(organizationDetails.getName());
                    organization.setDescription(organizationDetails.getDescription());
                    // Update other fields if necessary
                    return organizationRepository.save(organization);
                }).orElse(null);
    }

    // Delete an organization
    @Transactional
    public boolean deleteOrganization(Long organizationId) {
        Organization organization = organizationRepository.findById(organizationId).orElse(null);
        if (organization != null) {
            for (User user : organization.getMembers()) {
                user.getOrganizations().remove(organization);
                userRepository.save(user);
            }
            organizationRepository.delete(organization);
            return true;
        }
        return false;
    }



    // User joins an organization
    @Transactional
    public boolean joinOrganization(Long userId, Long organizationId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Organization> organization = organizationRepository.findById(organizationId);
        if (user.isPresent() && organization.isPresent()) {
            user.get().getOrganizations().add(organization.get());
            userRepository.save(user.get());
            return true;
        }
        return false;
    }

    // User leaves an organization
    @Transactional
    public boolean leaveOrganization(Long userId, Long organizationId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Organization> organization = organizationRepository.findById(organizationId);
        if (user.isPresent() && organization.isPresent()) {
            user.get().getOrganizations().remove(organization.get());
            userRepository.save(user.get());
            return true;
        }
        return false;
    }
}
