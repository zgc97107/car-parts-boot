package com.car.pojo;

import java.util.ArrayList;
import java.util.List;

public class MenuExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MenuExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andMenunameIsNull() {
            addCriterion("menuname is null");
            return (Criteria) this;
        }

        public Criteria andMenunameIsNotNull() {
            addCriterion("menuname is not null");
            return (Criteria) this;
        }

        public Criteria andMenunameEqualTo(String value) {
            addCriterion("menuname =", value, "menuname");
            return (Criteria) this;
        }

        public Criteria andMenunameNotEqualTo(String value) {
            addCriterion("menuname <>", value, "menuname");
            return (Criteria) this;
        }

        public Criteria andMenunameGreaterThan(String value) {
            addCriterion("menuname >", value, "menuname");
            return (Criteria) this;
        }

        public Criteria andMenunameGreaterThanOrEqualTo(String value) {
            addCriterion("menuname >=", value, "menuname");
            return (Criteria) this;
        }

        public Criteria andMenunameLessThan(String value) {
            addCriterion("menuname <", value, "menuname");
            return (Criteria) this;
        }

        public Criteria andMenunameLessThanOrEqualTo(String value) {
            addCriterion("menuname <=", value, "menuname");
            return (Criteria) this;
        }

        public Criteria andMenunameLike(String value) {
            addCriterion("menuname like", value, "menuname");
            return (Criteria) this;
        }

        public Criteria andMenunameNotLike(String value) {
            addCriterion("menuname not like", value, "menuname");
            return (Criteria) this;
        }

        public Criteria andMenunameIn(List<String> values) {
            addCriterion("menuname in", values, "menuname");
            return (Criteria) this;
        }

        public Criteria andMenunameNotIn(List<String> values) {
            addCriterion("menuname not in", values, "menuname");
            return (Criteria) this;
        }

        public Criteria andMenunameBetween(String value1, String value2) {
            addCriterion("menuname between", value1, value2, "menuname");
            return (Criteria) this;
        }

        public Criteria andMenunameNotBetween(String value1, String value2) {
            addCriterion("menuname not between", value1, value2, "menuname");
            return (Criteria) this;
        }

        public Criteria andUpmenuidIsNull() {
            addCriterion("upmenuid is null");
            return (Criteria) this;
        }

        public Criteria andUpmenuidIsNotNull() {
            addCriterion("upmenuid is not null");
            return (Criteria) this;
        }

        public Criteria andUpmenuidEqualTo(Integer value) {
            addCriterion("upmenuid =", value, "upmenuid");
            return (Criteria) this;
        }

        public Criteria andUpmenuidNotEqualTo(Integer value) {
            addCriterion("upmenuid <>", value, "upmenuid");
            return (Criteria) this;
        }

        public Criteria andUpmenuidGreaterThan(Integer value) {
            addCriterion("upmenuid >", value, "upmenuid");
            return (Criteria) this;
        }

        public Criteria andUpmenuidGreaterThanOrEqualTo(Integer value) {
            addCriterion("upmenuid >=", value, "upmenuid");
            return (Criteria) this;
        }

        public Criteria andUpmenuidLessThan(Integer value) {
            addCriterion("upmenuid <", value, "upmenuid");
            return (Criteria) this;
        }

        public Criteria andUpmenuidLessThanOrEqualTo(Integer value) {
            addCriterion("upmenuid <=", value, "upmenuid");
            return (Criteria) this;
        }

        public Criteria andUpmenuidIn(List<Integer> values) {
            addCriterion("upmenuid in", values, "upmenuid");
            return (Criteria) this;
        }

        public Criteria andUpmenuidNotIn(List<Integer> values) {
            addCriterion("upmenuid not in", values, "upmenuid");
            return (Criteria) this;
        }

        public Criteria andUpmenuidBetween(Integer value1, Integer value2) {
            addCriterion("upmenuid between", value1, value2, "upmenuid");
            return (Criteria) this;
        }

        public Criteria andUpmenuidNotBetween(Integer value1, Integer value2) {
            addCriterion("upmenuid not between", value1, value2, "upmenuid");
            return (Criteria) this;
        }

        public Criteria andMenupathIsNull() {
            addCriterion("menupath is null");
            return (Criteria) this;
        }

        public Criteria andMenupathIsNotNull() {
            addCriterion("menupath is not null");
            return (Criteria) this;
        }

        public Criteria andMenupathEqualTo(String value) {
            addCriterion("menupath =", value, "menupath");
            return (Criteria) this;
        }

        public Criteria andMenupathNotEqualTo(String value) {
            addCriterion("menupath <>", value, "menupath");
            return (Criteria) this;
        }

        public Criteria andMenupathGreaterThan(String value) {
            addCriterion("menupath >", value, "menupath");
            return (Criteria) this;
        }

        public Criteria andMenupathGreaterThanOrEqualTo(String value) {
            addCriterion("menupath >=", value, "menupath");
            return (Criteria) this;
        }

        public Criteria andMenupathLessThan(String value) {
            addCriterion("menupath <", value, "menupath");
            return (Criteria) this;
        }

        public Criteria andMenupathLessThanOrEqualTo(String value) {
            addCriterion("menupath <=", value, "menupath");
            return (Criteria) this;
        }

        public Criteria andMenupathLike(String value) {
            addCriterion("menupath like", value, "menupath");
            return (Criteria) this;
        }

        public Criteria andMenupathNotLike(String value) {
            addCriterion("menupath not like", value, "menupath");
            return (Criteria) this;
        }

        public Criteria andMenupathIn(List<String> values) {
            addCriterion("menupath in", values, "menupath");
            return (Criteria) this;
        }

        public Criteria andMenupathNotIn(List<String> values) {
            addCriterion("menupath not in", values, "menupath");
            return (Criteria) this;
        }

        public Criteria andMenupathBetween(String value1, String value2) {
            addCriterion("menupath between", value1, value2, "menupath");
            return (Criteria) this;
        }

        public Criteria andMenupathNotBetween(String value1, String value2) {
            addCriterion("menupath not between", value1, value2, "menupath");
            return (Criteria) this;
        }

        public Criteria andMenuStateIsNull() {
            addCriterion("menu_state is null");
            return (Criteria) this;
        }

        public Criteria andMenuStateIsNotNull() {
            addCriterion("menu_state is not null");
            return (Criteria) this;
        }

        public Criteria andMenuStateEqualTo(Integer value) {
            addCriterion("menu_state =", value, "menuState");
            return (Criteria) this;
        }

        public Criteria andMenuStateNotEqualTo(Integer value) {
            addCriterion("menu_state <>", value, "menuState");
            return (Criteria) this;
        }

        public Criteria andMenuStateGreaterThan(Integer value) {
            addCriterion("menu_state >", value, "menuState");
            return (Criteria) this;
        }

        public Criteria andMenuStateGreaterThanOrEqualTo(Integer value) {
            addCriterion("menu_state >=", value, "menuState");
            return (Criteria) this;
        }

        public Criteria andMenuStateLessThan(Integer value) {
            addCriterion("menu_state <", value, "menuState");
            return (Criteria) this;
        }

        public Criteria andMenuStateLessThanOrEqualTo(Integer value) {
            addCriterion("menu_state <=", value, "menuState");
            return (Criteria) this;
        }

        public Criteria andMenuStateIn(List<Integer> values) {
            addCriterion("menu_state in", values, "menuState");
            return (Criteria) this;
        }

        public Criteria andMenuStateNotIn(List<Integer> values) {
            addCriterion("menu_state not in", values, "menuState");
            return (Criteria) this;
        }

        public Criteria andMenuStateBetween(Integer value1, Integer value2) {
            addCriterion("menu_state between", value1, value2, "menuState");
            return (Criteria) this;
        }

        public Criteria andMenuStateNotBetween(Integer value1, Integer value2) {
            addCriterion("menu_state not between", value1, value2, "menuState");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}