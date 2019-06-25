package orangeschool.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "math_subject")
public class MathSubject extends AbstractModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer mathID;

	private Integer status;

	public Integer getId() {
		return mathID;
	}

	public void setId(Integer id) {
		this.mathID = id;
	}

	public MathSubject() {

	}


	public Integer getSubjectID() {
		return this.subject.getId();
	}

	public void setSubject(TextContent _subject) {
		this.subject = _subject;
	}

	public String getSubjectContent() {
		return this.subject.getContent();
	}

	public TextContent getSubject() {
		return this.subject;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer _status) {
		this.status = _status;
	}

	public String getDescriptionContent() {
		return this.description.getContent();
	}

	public TextContent getDescription() {
		return this.description;
	}

	public Integer getDescriptionID() {
		return this.description.getId();
	}

	public void setDescription(TextContent _description) {
		this.description = _description;
	}

	

	public void setTopic(Topic _topic) {
		this.topic = _topic;
	}

	public Integer getTopicID() {
		return this.topic.getId();
	}

	public Topic getTopic() {
		return this.topic;
	}

	public String getTopicName() {
		return this.topic.getName();
	}

	public Category getCategory() {
		return this.category;
	}

	public void setCategory(Category _category) {
		this.category = _category;
	}

	public Integer getCategoryID() {
		return this.category.getId();
	}

	
	public String getCategoryName() {
		return this.topic.getCategoryName();
	}
	
	public String toJsonString()
	{
		String ret = "{";
		
		ret +="\"i\":"; //subjectID
		ret +=this.getId();
		ret +=",";
		
		ret +="\"s\":\"";// subject 
		ret +=this.getSubjectContent();
		ret +="\",";
		
		ret +="\"d\":\"";// description
		ret +=this.getDescriptionContent();
		ret +="\",";
		
		ret +="}";
		
		return ret;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "topicID")
	private Topic topic;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "categoryID")
	private Category category;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "subjectID")
	private TextContent subject;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "descriptionID")
	private TextContent description;

}
