package platform.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;

@Entity
public class CodeSnippet {
    /*
        way to standardize date formatting between the API and the web interface.
        use: obj.formattedDate to call de getter to this.
        from: https://github.com/droideparanoico/codesharingplatform
     */
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name="UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    @JsonIgnore
    private UUID id;

    @Lob @Basic(optional = false)
    @NotNull
    private String code;

    @Column
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime date;

    @Column
    private Integer time;

    @Column
    private Integer views;

    @Column(updatable = false)
    @JsonIgnore
    private boolean timeRestricted;

    @Column(updatable = false)
    @JsonIgnore
    private boolean viewsRestricted;

    public CodeSnippet() {}

    public CodeSnippet(String code) {
        this.code = code;
        this.date = LocalDateTime.now();
    }

    public void checkRestrictions() {
        // check this only on create.
        Integer zero = 0;
        timeRestricted = time != null && zero.compareTo(time) < 0;
        viewsRestricted = views != null && zero.compareTo(views) < 0;
    }

    @JsonProperty("date")
    public String getFormattedDate() {
        return date == null ? "" : formatter.format(date);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CodeSnippet that = (CodeSnippet) o;

        if (timeRestricted != that.timeRestricted) return false;
        if (viewsRestricted != that.viewsRestricted) return false;
        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(code, that.code)) return false;
        if (!Objects.equals(date, that.date)) return false;
        if (!Objects.equals(time, that.time)) return false;
        return Objects.equals(views, that.views);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (views != null ? views.hashCode() : 0);
        result = 31 * result + (timeRestricted ? 1 : 0);
        result = 31 * result + (viewsRestricted ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CodeSnippet{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", date=" + date +
                ", time=" + time +
                ", views=" + views +
                ", timeRestricted=" + timeRestricted +
                ", viewsRestricted=" + viewsRestricted +
                '}';
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public boolean isTimeRestricted() {
        return timeRestricted;
    }

    public void setTimeRestricted(boolean timeRestricted) {
        this.timeRestricted = timeRestricted;
    }

    public boolean isViewsRestricted() {
        return viewsRestricted;
    }

    public void setViewsRestricted(boolean viewsRestricted) {
        this.viewsRestricted = viewsRestricted;
    }
}
