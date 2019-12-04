package hr.sedamit.demo.web.dto;

import hr.sedamit.demo.model.Address;
import hr.sedamit.demo.model.Author;
import hr.sedamit.demo.model.Book;
import hr.sedamit.demo.model.Member;
import hr.sedamit.demo.model.User;

public class DTOFactory {

	public static UserDTO toUserDTO(User user) {
		if (user == null)
			return null;
		else {
			return new UserDTO(user.getId(), user.getUsername(), user.getFullName(), user.getAge(),
					user.isActive());
		}
	}

	public static AuthorDTO toAuthorDTO(Author author) {
		if (author == null)
			return null;
		else {
			return new AuthorDTO(author.getId(), author.getFirstName(), author.getLastName(), author.getNationality(),
					author.getYearOfBirth());
		}
	}

	public static BookDTO toBookDTO(Book book) {
		if (book == null)
			return null;
		else {
			return new BookDTO(
					book.getId(),
					book.getTitle(),
					toAuthorDTO(book.getAuthor())
			);
		}
	}
	
	public static AddressDTO toAddressDTO(Address address) {
		if (address == null)
			return null;
		else {
			return new AddressDTO(
				address.getCountry(),
				address.getCity(),
				address.getStreet(),
				address.getStreetNumber()
			);
		}
	}

	public static MemberDTO toMemberDTO(Member member) {
		if (member == null)
			return null;
		else {
			return new MemberDTO(member.getId(), member.getUsername(), member.getFullName(), member.getAge(),
					member.isActive(), member.getMemberId(), toAddressDTO(member.getAddress()));
		}
	}
}
